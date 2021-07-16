package org.nagarro.disasterhelp.donor.services;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.nagarro.disasterhelp.donor.constants.Constants;
import org.nagarro.disasterhelp.donor.dao.ProviderDonateRepository;
import org.nagarro.disasterhelp.donor.model.ProviderOfferDetails;
import org.nagarro.disasterhelp.donor.model.ReceiverRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DonateService implements Constants {
	
	@Autowired
	private ProviderDonateRepository providerDonateRepository;
	
	@Autowired
	private OfferRequestMatchService offerRequestMatchService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ProviderOfferDetails getOfferById(int id) {
		ProviderOfferDetails offer = null;
		offer = this.providerDonateRepository.findById(id).get();
		return offer;
	}
	
	public List<ProviderOfferDetails> getAllOffer() {
		List<ProviderOfferDetails> offerList = null;
		offerList = this.providerDonateRepository.findAll();
		return offerList;
	}
	
	public List<ProviderOfferDetails> getOfferByProviderId(int id) {
		List<ProviderOfferDetails> offerList = null;
		offerList = this.providerDonateRepository.findByProviderId(id);
		return offerList;
	}
	
	
	public List<ProviderOfferDetails> getOfferByRequestId(int id) {
		List<ProviderOfferDetails> offerList = null;
		offerList = this.providerDonateRepository.findByRequestId(id);
		return offerList;
	}
	

	public ProviderOfferDetails addOffer(ProviderOfferDetails offer) {
		return  this.providerDonateRepository.save(offer);
	}

	
	public void deleteOffer(int id) throws JsonGenerationException, JsonMappingException, IOException {
		
		List<ReceiverRequestDetails> request = offerRequestMatchService.getReceiverRequestDetailsByOfferId(id);
		if((request.size() == 1) && (request.get(0).getId() == 0))
			return;
		if(request.size() != 0) {
			request.get(0).setOfferId(0);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request.get(0));
			String url = UPDATE_REQUEST_BY_ID_API + request.get(0).getId();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
			restTemplate.put(url, entity);		
		}
		this.providerDonateRepository.deleteById(id);
	}

	
	public void updateOffer(ProviderOfferDetails offer, int id) {
		offer.setId(id);
		this.providerDonateRepository.save(offer);
	}


}
