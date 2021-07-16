package org.nagarro.disasterhelp.donor.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.donor.constants.Constants;
import org.nagarro.disasterhelp.donor.model.ProviderOfferDetails;
import org.nagarro.disasterhelp.donor.model.Receiver;
import org.nagarro.disasterhelp.donor.model.ReceiverRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OfferRequestMatchService implements Constants{

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public ProviderOfferDetails match(ProviderOfferDetails offer)
			throws JsonParseException, JsonMappingException, IOException {
	
		List<ReceiverRequestDetails> requestList = getReceiverRequestDetailsByOfferId(0);
		if ((requestList.size() == 0) || ((requestList.size() == 1) && (requestList.get(0).getId() == 0)))
			return offer;
		
		List<ReceiverRequestDetails> matchedRequestsByAddress = getMatchedRequestByAddress(offer, requestList);		
		if (matchedRequestsByAddress.size() == 0)
			return offer;
		
		ReceiverRequestDetails targetRequest = getMatchedRequestByContent(offer, matchedRequestsByAddress);
		if (targetRequest != null) {
			offer.setRequestId(targetRequest.getId());
			targetRequest.setOfferId(offer.getId());
			updateRequestById(targetRequest);
		}

		return offer;
	}
	
	

	public void updateRequestById(ReceiverRequestDetails targetRequest)
			throws JsonGenerationException, JsonMappingException, IOException {	
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(targetRequest);
		String url = UPDATE_REQUEST_BY_ID_API + targetRequest.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		restTemplate.put(url, entity);
	}
	
	

	public List<ReceiverRequestDetails> getReceiverRequestDetailsByOfferId(int offerId)
			throws JsonParseException, JsonMappingException, IOException {
	
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(RECEIVER_REQUEST_SERVICE_CIRCUIT_BREAKER);
		
		List<ReceiverRequestDetails> requestsList = circuitBreaker.run(() -> {
			List<ReceiverRequestDetails> requestList = null;
			ResponseEntity<String> entity = restTemplate.getForEntity(GET_REQUEST_BY_OFFER_ID_API + offerId,
					String.class);	
			String body = entity.getBody();
			
			ReceiverRequestDetails[] requests = null;
			
			try {
				requests = new ObjectMapper().readValue(body, ReceiverRequestDetails[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			requestList = Arrays.asList(requests);
			return requestList;
		}, throwable -> {
			List<ReceiverRequestDetails> requestList = new ArrayList<ReceiverRequestDetails>();
			requestList.add(new ReceiverRequestDetails());
			return requestList;
		});
		return requestsList;
	}
	
	
	public List<ReceiverRequestDetails> getMatchedRequestByAddress(ProviderOfferDetails offer, List<ReceiverRequestDetails> requestList){
		
		List<ReceiverRequestDetails> matchedRequestsByAddress = new ArrayList<ReceiverRequestDetails>();	
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("receiverRegistrationService-cb");
		
		Receiver receiverCheck = circuitBreaker.run(() -> {
			return this.restTemplate.getForObject(GET_RECEIVER_BY_ID_API + requestList.get(0).getReceiverId(), Receiver.class);			
		}, throwable -> {
			return null;
		});	
		if(receiverCheck == null)
			return matchedRequestsByAddress;
	
		for (ReceiverRequestDetails request : requestList) {
			Receiver receiver = this.restTemplate
					.getForObject(GET_RECEIVER_BY_ID_API + request.getReceiverId(), Receiver.class);
			if (receiver.getReceiverAddress().getCity().equals(offer.getProviderAddress().getCity())) {
				matchedRequestsByAddress.add(request);
			}
		}
		return matchedRequestsByAddress;	
	}
	

	public ReceiverRequestDetails getMatchedRequestByContent(ProviderOfferDetails offer,
			List<ReceiverRequestDetails> requestList) {

		HashMap<Integer, ReceiverRequestDetails> matchedRequestByContent = new HashMap<Integer, ReceiverRequestDetails>();

		if(offer.getFood() != null || offer.getCloth() != null) {	
			for (ReceiverRequestDetails request : requestList) {
				if (offer.getFood() != null && offer.getCloth() != null && request.getFood() != null
						&& request.getCloth() != null) {				
					return request;
				}
		
				if (offer.getFood() != null && request.getFood() != null) {
					matchedRequestByContent.put(1, request);
					continue;
				}
		
				if (offer.getCloth() != null && request.getCloth() != null) {
					matchedRequestByContent.put(1, request);
					continue;
				}
			
			}
			return matchedRequestByContent.get(1);
		}
			
			// -------------------------------------------------------------------
		
		else{
			
			for (ReceiverRequestDetails request : requestList) {
				
				if (offer.getBlood() != null && request.getBlood() != null && offer.getMedicine() != null
						&& request.getMedicine() != null && offer.getMedical_apparatus() != null
						&& request.getMedical_apparatus() != null) {
					
					List<String> offerBloodList = Arrays.asList(offer.getBlood().split(","));
					List<String> requestBloodList = Arrays.asList(request.getBlood().split(","));
					List<String> offerMedicineList = Arrays.asList(offer.getMedicine().split(","));
					List<String> requestMedicineList = Arrays.asList(request.getMedicine().split(","));
					List<String> offerMedicalAppararusList = Arrays.asList(offer.getMedical_apparatus().split(","));
					List<String> requestMedicalApparatusList = Arrays.asList(request.getMedical_apparatus().split(","));
	
					List<String> checkBlood = offerBloodList.stream().filter(requestBloodList::contains)
							.collect(Collectors.toList());
					List<String> checkMedicine = offerMedicineList.stream().filter(requestMedicineList::contains)
							.collect(Collectors.toList());
					List<String> checkMedicalApparatus = offerMedicalAppararusList.stream()
							.filter(requestMedicalApparatusList::contains).collect(Collectors.toList());
					
					if (checkBlood.size() != 0 && checkMedicine.size() != 0 && checkMedicalApparatus.size() != 0) {
						return request;
					}					
				}
				
				
				if (offer.getBlood() != null && request.getBlood() != null && offer.getMedicine() != null
						&& request.getMedicine() != null) {
					
					List<String> offerBloodList = Arrays.asList(offer.getBlood().split(","));
					List<String> requestBloodList = Arrays.asList(request.getBlood().split(","));
					List<String> offerMedicineList = Arrays.asList(offer.getMedicine().split(","));
					List<String> requestMedicineList = Arrays.asList(request.getMedicine().split(","));
					
					List<String> checkBlood = offerBloodList.stream().filter(requestBloodList::contains)
							.collect(Collectors.toList());
					List<String> checkMedicine = offerMedicineList.stream().filter(requestMedicineList::contains)
							.collect(Collectors.toList());
					
					
					if (checkBlood.size() != 0 && checkMedicine.size() != 0) {
						return request;
					}					
				}
				
				
				if (offer.getMedicine() != null && request.getMedicine() != null && offer.getMedical_apparatus() != null
						&& request.getMedical_apparatus() != null) {
					
					List<String> offerMedicineList = Arrays.asList(offer.getMedicine().split(","));
					List<String> requestMedicineList = Arrays.asList(request.getMedicine().split(","));
					List<String> offerMedicalAppararusList = Arrays.asList(offer.getMedical_apparatus().split(","));
					List<String> requestMedicalApparatusList = Arrays.asList(request.getMedical_apparatus().split(","));
	
					List<String> checkMedicine = offerMedicineList.stream().filter(requestMedicineList::contains)
							.collect(Collectors.toList());
					List<String> checkMedicalApparatus = offerMedicalAppararusList.stream()
							.filter(requestMedicalApparatusList::contains).collect(Collectors.toList());
					
					if (checkMedicine.size() != 0 && checkMedicalApparatus.size() != 0) {
						return request;
					}						
				}
				
				
				if (offer.getBlood() != null && request.getBlood() != null && offer.getMedical_apparatus() != null
						&& request.getMedical_apparatus() != null) {
					
					List<String> offerBloodList = Arrays.asList(offer.getBlood().split(","));
					List<String> requestBloodList = Arrays.asList(request.getBlood().split(","));
					List<String> offerMedicalAppararusList = Arrays.asList(offer.getMedical_apparatus().split(","));
					List<String> requestMedicalApparatusList = Arrays.asList(request.getMedical_apparatus().split(","));
	
					List<String> checkBlood = offerBloodList.stream().filter(requestBloodList::contains)
							.collect(Collectors.toList());
					List<String> checkMedicalApparatus = offerMedicalAppararusList.stream()
							.filter(requestMedicalApparatusList::contains).collect(Collectors.toList());				
					if (checkBlood.size() != 0 && checkMedicalApparatus.size() != 0) {
						return request;
					}						
				}
				
	
				if (offer.getBlood() != null && request.getBlood() != null) {
	
					List<String> offerBloodList = Arrays.asList(offer.getBlood().split(","));
					List<String> requestBloodList = Arrays.asList(request.getBlood().split(","));
					List<String> checkBlood = offerBloodList.stream().filter(requestBloodList::contains)
							.collect(Collectors.toList());
					if (checkBlood.size() != 0) {
						matchedRequestByContent.put(1, request);
						continue;}
				}
	
				if (offer.getMedicine() != null && request.getMedicine() != null) {
	
					List<String> offerMedicineList = Arrays.asList(offer.getMedicine().split(","));
					List<String> requestMedicineList = Arrays.asList(request.getMedicine().split(","));
					List<String> checkMedicine = offerMedicineList.stream().filter(requestMedicineList::contains)
							.collect(Collectors.toList());
					if (checkMedicine.size() != 0) {
						matchedRequestByContent.put(1, request);
						continue;}
				}
	
				if (offer.getMedical_apparatus() != null && request.getMedical_apparatus() != null) {
	
					List<String> offerMedicalAppararusList = Arrays.asList(offer.getMedical_apparatus().split(","));
					List<String> requestMedicalApparatusList = Arrays.asList(request.getMedical_apparatus().split(","));
					List<String> checkMedicalApparatus = offerMedicalAppararusList.stream()
							.filter(requestMedicalApparatusList::contains).collect(Collectors.toList());
					if (checkMedicalApparatus.size() != 0){
						matchedRequestByContent.put(1, request);
						continue; }
				}			
			}
			
			if(matchedRequestByContent.size() == 0)
				return null;
			else
				return matchedRequestByContent.get(1);
		}		
	}
}
