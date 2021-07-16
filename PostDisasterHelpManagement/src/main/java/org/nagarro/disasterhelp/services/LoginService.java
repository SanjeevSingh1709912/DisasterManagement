package org.nagarro.disasterhelp.services;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.constants.ServiceConstants;
import org.nagarro.disasterhelp.models.Provider;
import org.nagarro.disasterhelp.models.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService implements ServiceConstants {
	
	@Autowired
	private RestTemplate restTemplate;


	public Provider getProviderByEamilAndPassword(String email, String password)
			throws JsonParseException, JsonMappingException, IOException {

		Provider provider = null;
		if (email.isBlank() || password.isBlank()) {
			return provider;
		}
		String url = GET_PROVIDER_API+email+"/"+password;
		ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
		if (entity.getBody() == null) {
			return provider;
		}
		if (entity.getStatusCodeValue() == 200) {
			String body = entity.getBody();
			provider = new ObjectMapper().readValue(body, Provider.class);
			return provider;
		} else {
			return provider;
		}
	}
	
	
	
	public Receiver getReceiverByEmailAndPassword(String email, String password)
			throws JsonParseException, JsonMappingException, IOException {

		Receiver receiver = null;
		if (email.isBlank() || password.isBlank()) {
			return receiver;
		}
		String url = GET_RECEIVER_API + email+ "/" + password;
		ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
		if (entity.getBody() == null) {
			return receiver;
		}
		if (entity.getStatusCodeValue() == 200) {
			String body = entity.getBody();
			receiver = new ObjectMapper().readValue(body, Receiver.class);
			return receiver;
		} else {
			return receiver;
		}
	}

}
