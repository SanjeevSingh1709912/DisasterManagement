package org.nagarro.disasterhelp.consumer.services;

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
import org.nagarro.disasterhelp.consumer.constants.Constants;
import org.nagarro.disasterhelp.consumer.models.ProviderOfferDetails;
import org.nagarro.disasterhelp.consumer.models.Receiver;
import org.nagarro.disasterhelp.consumer.models.ReceiverRequestDetails;
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
public class RequestOfferMatchService implements Constants {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public ReceiverRequestDetails match(ReceiverRequestDetails request)
			throws JsonParseException, JsonMappingException, IOException {

		List<ProviderOfferDetails> offerList = getProviderOfferDetailsByRequestId(0);
		if (offerList.size() == 0)
			return request;
		if((offerList.size() == 1) && (offerList.get(0).getId() == 0)) {
			return request;
		}
		
		List<ProviderOfferDetails> matchedOffersByAddress = new ArrayList<ProviderOfferDetails>();
			
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(RECEIVER_REGISTRATION_SERVICE_CIRCUIT_BREAKER);		
		Receiver receiver = circuitBreaker.run(() -> {
			return this.restTemplate.getForObject(GET_RECEIVER_BY_ID_API + request.getReceiverId(),
					Receiver.class);		
		}, throwable -> {
			return null;
		});	
		if(receiver == null)
			return request;
	
		for (ProviderOfferDetails offer : offerList) {
			if (receiver.getReceiverAddress().getCity().equals(offer.getProviderAddress().getCity())) {
				matchedOffersByAddress.add(offer);
			}
		}
		if (matchedOffersByAddress.size() == 0)
			return request;
			
		ProviderOfferDetails targetOffer = getMatchedOfferByContent(request, matchedOffersByAddress);	

		if (targetOffer != null) {
			targetOffer.setRequestId(request.getId());
			request.setOfferId(targetOffer.getId());
			updateOfferById(targetOffer);
		}

		return request;
	}

	
	public void updateOfferById(ProviderOfferDetails targetOffer)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(targetOffer);
		String url = UPDATE_OFFER_BY_ID_API + targetOffer.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		restTemplate.put(url, entity);
	}
	
	
	public List<ProviderOfferDetails> getProviderOfferDetailsByRequestId(int requestId)
			throws JsonParseException, JsonMappingException, IOException {
			
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(PROVIDER_OFFER_SERVICE_CIRCUIT_BREAKER);
		
		List<ProviderOfferDetails> offersList = circuitBreaker.run(() -> {
			List<ProviderOfferDetails> offerList = null;
			ResponseEntity<String> entity = restTemplate.getForEntity(GET_OFFER_BY_REQUEST_ID + requestId,
					String.class);	
			String body = entity.getBody();
			
			ProviderOfferDetails[] offers = null;
			
			try {
				offers = new ObjectMapper().readValue(body, ProviderOfferDetails[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			offerList = Arrays.asList(offers);
			return offerList;
		}, throwable -> {
			List<ProviderOfferDetails> offerList = new ArrayList<ProviderOfferDetails>();
			offerList.add(new ProviderOfferDetails());
			return offerList;
		});
		return offersList;
	}

	
	public ProviderOfferDetails getMatchedOfferByContent(ReceiverRequestDetails request,
			List<ProviderOfferDetails> offerList) {

		HashMap<Integer, ProviderOfferDetails> matchedOfferByContent = new HashMap<Integer, ProviderOfferDetails>();

		if(request.getFood() != null || request.getCloth() != null) {
			
			for (ProviderOfferDetails offer : offerList) {
	
				if (offer.getFood() != null && offer.getCloth() != null && request.getFood() != null
						&& request.getCloth() != null) {
					return offer;
				}
	
				if (offer.getFood() != null && request.getFood() != null) {
					matchedOfferByContent.put(1, offer);
					continue;
				}
	
				if (offer.getCloth() != null && request.getCloth() != null) {
					matchedOfferByContent.put(1, offer);
					continue;
				}
			}
			return matchedOfferByContent.get(1);
		}

			// -------------------------------------------------------------------

		else{
			
			for (ProviderOfferDetails offer : offerList) {
				
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
						return offer;
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
						return offer;
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
						return offer;
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
						return offer;
					}						
				}
				
	
				if (offer.getBlood() != null && request.getBlood() != null) {
	
					List<String> offerBloodList = Arrays.asList(offer.getBlood().split(","));
					List<String> requestBloodList = Arrays.asList(request.getBlood().split(","));
					List<String> checkBlood = offerBloodList.stream().filter(requestBloodList::contains)
							.collect(Collectors.toList());
					if (checkBlood.size() != 0) {
						matchedOfferByContent.put(1, offer);
						continue;}
				}
	
				if (offer.getMedicine() != null && request.getMedicine() != null) {
	
					List<String> offerMedicineList = Arrays.asList(offer.getMedicine().split(","));
					List<String> requestMedicineList = Arrays.asList(request.getMedicine().split(","));
					List<String> checkMedicine = offerMedicineList.stream().filter(requestMedicineList::contains)
							.collect(Collectors.toList());
					if (checkMedicine.size() != 0) {
						matchedOfferByContent.put(1, offer);
						continue;}
				}
	
				if (offer.getMedical_apparatus() != null && request.getMedical_apparatus() != null) {
	
					List<String> offerMedicalAppararusList = Arrays.asList(offer.getMedical_apparatus().split(","));
					List<String> requestMedicalApparatusList = Arrays.asList(request.getMedical_apparatus().split(","));
					List<String> checkMedicalApparatus = offerMedicalAppararusList.stream()
							.filter(requestMedicalApparatusList::contains).collect(Collectors.toList());
					if (checkMedicalApparatus.size() != 0){
						matchedOfferByContent.put(1, offer);
						continue; }
				}			
			}
			
			if(matchedOfferByContent.size() == 0)
				return null;
			else
				return matchedOfferByContent.get(1);
		}	
	}
}
