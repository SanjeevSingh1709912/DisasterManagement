package org.nagarro.disasterhelp.contollers;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nagarro.disasterhelp.constants.ControllerConstants;
import org.nagarro.disasterhelp.models.ProviderAddress;
import org.nagarro.disasterhelp.models.ProviderOfferDetails;
import org.nagarro.disasterhelp.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.regex.*;

@Controller
@RequestMapping("/DisasterHelpForm")
public class ProviderOffersController implements ControllerConstants {
	
	@Autowired
	private ProviderService providerService;
	
	@PostMapping(PROVIDER_FORM1_TASK)	
	public String providerForm1Task(		
			@RequestParam(name = COUNTRY, defaultValue = "") String country,
			@RequestParam(name = STATE, defaultValue = "") String state,
			@RequestParam(name = CITY, defaultValue = "") String city,
			@RequestParam(name = PINCODE, defaultValue = "") String pincode,
			@RequestParam(name = FOOD, defaultValue = "") String food,
			@RequestParam(name = CLOTH, defaultValue = "") String cloth,
			@RequestParam(name = DESCRIPTION, defaultValue = "") String desc,
			Model model
			) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(country.isEmpty() || state.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, ADDRESS_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM1_PAGE;
		}
		
		if(food.isEmpty() && cloth.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, SELECT_FROM_FOOD_CLOTH);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM1_PAGE;
		}
		if(desc.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, DESCRIPTION_BLANK_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM1_PAGE;
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(pincode);
		if((!matcher.find()) || pincode.length() != 6) {
			model.addAttribute(PINCODE_ERROR_LABEL, INVALID_PINCODE_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc,FORM_ERROR_LABEL,"");
			model.addAllAttributes(address);
			return PROVIDER_FORM1_PAGE;
		}
		
		ProviderOfferDetails offer = new ProviderOfferDetails();
		ProviderAddress providerAddress = new ProviderAddress(country, state, city, pincode);
		if (food.isEmpty())
			offer.setFood(null);
		else
			offer.setFood(food);
		if (cloth.isEmpty())
			offer.setCloth(null);
		else
			offer.setCloth(cloth);
		offer.setProviderId(LoginController.loggedInProvider.getId());
		offer.setDescription(desc);
		offer.setProviderAddress(providerAddress);
		providerService.addOffer(offer);
		return REDIRECT_TO_MYDONATION_PAGE;
	}
	
	@PostMapping(PROVIDER_FORM2_TASK)
	public String providerForm2Task(		
			@RequestParam(COUNTRY) String country,
			@RequestParam(STATE) String state,
			@RequestParam(CITY) String city,
			@RequestParam(PINCODE) String pincode,
			@RequestParam(name = DESCRIPTION, defaultValue = "") String desc,
			@RequestParam(name = MEDICINE, defaultValue = "") String medicine,
			@RequestParam(name = MEDICINE_CONTENT, defaultValue = "") String medContent,
			@RequestParam(name = BLOOD, defaultValue = "") String blood,
			@RequestParam(name = BLOOD_CONTENT, defaultValue = "") String bloodContent,
			@RequestParam(name = APPARATUS, defaultValue = "") String apparatus,
			@RequestParam(name = APPARATUS_CONTENT, defaultValue = "") String apparatusContent,
			Model model
			) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(country.isEmpty() || state.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, ADDRESS_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM2_PAGE;
		}
		
		if(desc.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, DESCRIPTION_BLANK_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM2_PAGE;
		}
		
		if(medContent.isEmpty() && bloodContent.isEmpty() && apparatusContent.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, SELECT_FROM_MEDICINE_BLOOD_APPARATUS);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc);
			model.addAllAttributes(address);
			return PROVIDER_FORM2_PAGE;
		}	
		
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(pincode);
		if((!matcher.find()) || pincode.length() != 6) {
			model.addAttribute(PINCODE_ERROR_LABEL, INVALID_PINCODE_ERROR);
			Map<String, String> address = Map.of(COUNTRY,country,STATE,state,CITY,city,PINCODE,pincode,"desc",desc,FORM_ERROR_LABEL,"");
			model.addAllAttributes(address);
			return PROVIDER_FORM2_PAGE;
		}
		
		ProviderOfferDetails offer = new ProviderOfferDetails();
		ProviderAddress providerAddress = new ProviderAddress(country, state, city, pincode);
		offer.setProviderId(LoginController.loggedInProvider.getId());
		offer.setDescription(desc);
		offer.setProviderAddress(providerAddress);
		if(medicine.isEmpty())
			offer.setMedicine(null);
		else
			offer.setMedicine(medContent);
		if(blood.isEmpty())
			offer.setBlood(null);
		else
			offer.setBlood(bloodContent);
		if(apparatus.isEmpty())
			offer.setMedical_apparatus(null);
		else
			offer.setMedical_apparatus(apparatusContent);
		providerService.addOffer(offer);
		return REDIRECT_TO_MYDONATION_PAGE;
	}
	
	@GetMapping(DELETE_OFFER_TASK)
	public String offerDeleteTask(@RequestParam("offerId") int offerId) {
		
		providerService.deleteOffer(offerId);
		return REDIRECT_TO_MYDONATION_PAGE;
	}

}
