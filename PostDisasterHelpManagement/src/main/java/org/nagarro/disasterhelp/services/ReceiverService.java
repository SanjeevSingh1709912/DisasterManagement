package org.nagarro.disasterhelp.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.constants.ServiceConstants;
import org.nagarro.disasterhelp.models.Receiver;
import org.nagarro.disasterhelp.models.ReceiverRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class ReceiverService implements ServiceConstants {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public void addRequest(ReceiverRequestDetails request) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);
		Client client = Client.create();
		WebResource webResource = client.resource(ADD_REQUEST_API);
		ClientResponse response = webResource.type(APPLICATION_CONTENT).post(ClientResponse.class, json);
	}
	
	
	public List<ReceiverRequestDetails>  getRequestsByReceiverId(int id) throws JsonParseException, JsonMappingException, IOException {	
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_REQUEST_BY_RECEIVER_ID_API+id, String.class);
		List<ReceiverRequestDetails> requestsList = null;
		String body = entity.getBody();
		ReceiverRequestDetails[] requests = new ObjectMapper().readValue(body, ReceiverRequestDetails[].class);
		requestsList = Arrays.asList(requests);
		return requestsList;		
	}
	
	public Boolean signupEmailValidation(String email) throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_RECEIVER_BY_EMAIL+email, String.class);
		if (entity.getStatusCodeValue() == 200) {
			if (entity.getBody() == null)
				return false;
			return true;
		} else {
			return true;
		}
	}
	
	public ReceiverRequestDetails  getRequestById(int id) throws JsonParseException, JsonMappingException, IOException {	
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_REQUEST_BY_ID_API+id, String.class);
		String body = entity.getBody();
		ReceiverRequestDetails requests = new ObjectMapper().readValue(body, ReceiverRequestDetails.class);	
		return requests;		
	}
	
	public Receiver getReceiverById(int id) throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_RECEIVER_API+id, String.class);
		String body = entity.getBody();
		Receiver receiver = new ObjectMapper().readValue(body, Receiver.class);	
		return receiver;
	}
	
	public void deleteRequest(int id) {
		restTemplate.delete(DELETE_REQUEST_API+id);
	}


}
