package org.nagarro.disasterhelp.services;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.constants.ServiceConstants;
import org.nagarro.disasterhelp.models.Provider;
import org.nagarro.disasterhelp.models.Receiver;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class RegistrationService implements ServiceConstants {

	public void addProvider(Provider provider) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(provider);
		Client client = Client.create();
		WebResource webResource = client.resource(ADD_PROVIDER_API);
		ClientResponse response = webResource.type(APPLICATION_CONTENT).post(ClientResponse.class, json);
	}
	
	
	public void addReceiver(Receiver receiver) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.print(receiver);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(receiver);
		Client client = Client.create();
		WebResource webResource = client.resource(ADD_RECEIVER_API);
		
		ClientResponse response = webResource.type(APPLICATION_CONTENT).post(ClientResponse.class, json);
	}

}
