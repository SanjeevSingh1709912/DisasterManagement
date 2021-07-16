package org.nagarro.disasterhelp.consumer.controllers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nagarro.disasterhelp.consumer.models.ReceiverRequestDetails;
import org.nagarro.disasterhelp.consumer.services.RequestOfferMatchService;
import org.nagarro.disasterhelp.consumer.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private RequestOfferMatchService requestOfferMatchService;

	@GetMapping("/requests/{id}")
	public ReceiverRequestDetails getRequestById(@PathVariable("id") int id) {	
		return this.requestService.getRequestById(id);
	}
	
	@GetMapping("/requests")
	public List<ReceiverRequestDetails> getAllRequests() {	
		return this.requestService.getAllRequests();
	}
	
	@GetMapping("/requests/receiver/{id}")
	public List<ReceiverRequestDetails> getRequestByReceiverId(@PathVariable("id") int id) {	
		return this.requestService.getRequestByReceiverId(id);
	}
	
	@GetMapping("/requests/offerid/{id}")
	public List<ReceiverRequestDetails> getRequestByOfferId(@PathVariable("id") int id) {	
		return this.requestService.getRequestByOfferId(id);
	}
	
	@PostMapping("/requests")
	public void addRequest(@RequestBody ReceiverRequestDetails request) throws JsonParseException, JsonMappingException, IOException {	
		ReceiverRequestDetails savedRequest = this.requestService.addRequest(request);
		ReceiverRequestDetails matchedRequest = requestOfferMatchService.match(savedRequest);
		if(matchedRequest.getOfferId() != 0) {
			this.requestService.updateRequest(matchedRequest, matchedRequest.getId());
		}
	}
	
	@DeleteMapping("/requests/{id}")
	public void deleteRequest(@PathVariable("id") int id) throws JsonParseException, JsonMappingException, IOException {	
		this.requestService.deleteRequest(id);
	}
	
	@PutMapping("/requests/{id}")
	public void updateRequest(@RequestBody ReceiverRequestDetails request, @PathVariable("id") int id) {		
		this.requestService.updateRequest(request, id);
	}
	
	
}
