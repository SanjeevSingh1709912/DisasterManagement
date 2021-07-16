package org.nagarro.disasterhelp.donor.controllers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nagarro.disasterhelp.donor.model.ProviderOfferDetails;
import org.nagarro.disasterhelp.donor.services.DonateService;
import org.nagarro.disasterhelp.donor.services.OfferRequestMatchService;
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
	private DonateService donateService;
	
	@Autowired
	private OfferRequestMatchService offerRequestMatchService;
	
	
	@GetMapping("/offers")
	public List<ProviderOfferDetails> getAllOffers() {	
		return this.donateService.getAllOffer();
	}
	
	@GetMapping("/offers/provider/{id}")
	public List<ProviderOfferDetails> getOfferByProviderId(@PathVariable("id") int id) {	
		return this.donateService.getOfferByProviderId(id);
	}
	
	@GetMapping("/offers/{id}")
	public ProviderOfferDetails getOfferById(@PathVariable("id") int id) {	
		return this.donateService.getOfferById(id);
	}
	
	@GetMapping("/offers/requestid/{id}")
	public List<ProviderOfferDetails> getOfferByRequestId(@PathVariable("id") int id) {	
		return this.donateService.getOfferByRequestId(id);
	}
	
	@PostMapping("/offers")
	public void addProvider(@RequestBody ProviderOfferDetails offer) throws JsonParseException, JsonMappingException, IOException {	
		
		ProviderOfferDetails savedOffer = this.donateService.addOffer(offer);	
		ProviderOfferDetails matchedOffer = this.offerRequestMatchService.match(savedOffer);
		if(matchedOffer.getRequestId() != 0) {
			this.donateService.updateOffer(matchedOffer, matchedOffer.getId());
		}		
	}
	
	@DeleteMapping("/offers/{id}")
	public void deleteOffer(@PathVariable("id") int id) throws JsonGenerationException, JsonMappingException, IOException {	
		this.donateService.deleteOffer(id);
	}
	
	@PutMapping("/offers/{id}")
	public void updateOffer(@RequestBody ProviderOfferDetails offer, @PathVariable("id") int id) {		
		this.donateService.updateOffer(offer, id);
	}
	
	
}
