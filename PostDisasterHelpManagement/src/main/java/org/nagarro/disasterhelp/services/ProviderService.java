package org.nagarro.disasterhelp.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.constants.ServiceConstants;
import org.nagarro.disasterhelp.models.Provider;
import org.nagarro.disasterhelp.models.ProviderOfferDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class ProviderService implements ServiceConstants {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void addOffer(ProviderOfferDetails offer) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(offer);
		Client client = Client.create();
		WebResource webResource = client.resource(ADD_OFFER_API);
		ClientResponse response = webResource.type(APPLICATION_CONTENT).post(ClientResponse.class, json);
	}

	
	
	public List<ProviderOfferDetails>  getOffersByProviderId(int id) throws JsonParseException, JsonMappingException, IOException {	
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_OFFER_BY_PROVIDER_ID_API+id, String.class);
		List<ProviderOfferDetails> donationsList = null;
		String body = entity.getBody();
		ProviderOfferDetails[] donations = new ObjectMapper().readValue(body, ProviderOfferDetails[].class);
		donationsList = Arrays.asList(donations);
		return donationsList;		
	}

	
	public ProviderOfferDetails  getOfferById(int id) throws JsonParseException, JsonMappingException, IOException {	
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_OFFER_BY_ID_API+id, String.class);
		String body = entity.getBody();
		ProviderOfferDetails offer = new ObjectMapper().readValue(body, ProviderOfferDetails.class);	
		return offer;		
	}

	public Provider getProviderById(int id) throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_PROVIDER_API+id, String.class);
		String body = entity.getBody();
		Provider provider = new ObjectMapper().readValue(body, Provider.class);	
		return provider;
	}
	
	public Boolean signupEmailValidation(String email) throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity(GET_PROVIDER_BY_EMAIL_API+email, String.class);
		if (entity.getStatusCodeValue() == 200) {
			if (entity.getBody() == null)
				return false;
			return true;
		} else {
			return true;
		}
	}

	public void deleteOffer(int id) {
		restTemplate.delete(DELETE_OFFER_API+id);
	}
}
