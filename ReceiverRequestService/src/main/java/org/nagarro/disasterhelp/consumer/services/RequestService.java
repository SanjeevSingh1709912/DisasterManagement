package org.nagarro.disasterhelp.consumer.services;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.consumer.constants.Constants;
import org.nagarro.disasterhelp.consumer.dao.ReceiverRequestRepository;
import org.nagarro.disasterhelp.consumer.models.ProviderOfferDetails;
import org.nagarro.disasterhelp.consumer.models.ReceiverRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService implements Constants {
	
	@Autowired
	private ReceiverRequestRepository receiverRequestRepository;
	
	@Autowired
	private RequestOfferMatchService requestOfferMatchService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ReceiverRequestDetails getRequestById(int id) {
		ReceiverRequestDetails request = null;
		request = this.receiverRequestRepository.findById(id).get();
		return request;
	}
	
	public List<ReceiverRequestDetails> getAllRequests() {
		List<ReceiverRequestDetails> requestList = null;
		requestList = this.receiverRequestRepository.findAll();
		return requestList;
	}
	
	public List<ReceiverRequestDetails> getRequestByReceiverId(int id) {
		List<ReceiverRequestDetails> requestList = null;
		requestList = this.receiverRequestRepository.findByReceiverId(id);
		return requestList;
	}
		
	
	public List<ReceiverRequestDetails> getRequestByOfferId(int id) {
		List<ReceiverRequestDetails> requestList = null;
		requestList = this.receiverRequestRepository.findByOfferId(id);
		return requestList;
	}
	

	public ReceiverRequestDetails addRequest(ReceiverRequestDetails request) {
		return this.receiverRequestRepository.save(request);
	}

	
	public void deleteRequest(int id) throws JsonParseException, JsonMappingException, IOException {
		
		List<ProviderOfferDetails> offer = requestOfferMatchService.getProviderOfferDetailsByRequestId(id);
		if((offer.size() == 1) && (offer.get(0).getId() == 0)) {
			return;
		}	
		if(offer.size() != 0) {
			offer.get(0).setRequestId(0);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(offer.get(0));
			String url = UPDATE_OFFER_BY_ID_API + offer.get(0).getId();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
			restTemplate.put(url, entity);		
		}
		this.receiverRequestRepository.deleteById(id);
	}

	
	public void updateRequest(ReceiverRequestDetails request, int id) {
		request.setId(id);
		this.receiverRequestRepository.save(request);
	}


}
