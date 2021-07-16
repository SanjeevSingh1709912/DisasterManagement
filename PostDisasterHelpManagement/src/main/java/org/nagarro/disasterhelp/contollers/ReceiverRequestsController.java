package org.nagarro.disasterhelp.contollers;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nagarro.disasterhelp.constants.ControllerConstants;
import org.nagarro.disasterhelp.models.ReceiverRequestDetails;
import org.nagarro.disasterhelp.services.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/DisasterHelpForm")
public class ReceiverRequestsController implements ControllerConstants {

	@Autowired
	private ReceiverService receiverService;
	
	@PostMapping(RECEIVER_FORM1_TASK)	
	public String receiverForm1Task(	
			@RequestParam(name = FOOD, defaultValue = "") String food,
			@RequestParam(name = CLOTH, defaultValue = "") String cloth,
			@RequestParam(name = DESCRIPTION, defaultValue = "") String desc, Model model
			) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(food.isEmpty() && cloth.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, SELECT_FROM_FOOD_CLOTH);
			return RECEIVER_FORM1_PAGE;
		}
		if(desc.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, DESCRIPTION_BLANK_ERROR);
			return RECEIVER_FORM1_PAGE;
		}
			
		ReceiverRequestDetails request = new ReceiverRequestDetails();
		if (food.isEmpty())
			request.setFood(null);	
		else
			request.setFood(food);
		
		if (cloth.isEmpty())
			request.setCloth(null);
		else
			request.setCloth(cloth);
		
		request.setReceiverId(LoginController.loggedInReceiver.getId());
		request.setDescription(desc);

		receiverService.addRequest(request);
		return REDIRECT_TO_MYREQUEST_PAGE;
	}
	
	
	@PostMapping(RECEIVER_FORM2_TASK)
	public String receiverForm2Task(
			@RequestParam(name = DESCRIPTION, defaultValue = "") String desc,
			@RequestParam(name = MEDICINE, defaultValue = "") String medicine,
			@RequestParam(name = MEDICINE_CONTENT, defaultValue = "") String medContent,
			@RequestParam(name = BLOOD, defaultValue = "") String blood,
			@RequestParam(name = BLOOD_CONTENT, defaultValue = "") String bloodContent,
			@RequestParam(name = APPARATUS, defaultValue = "") String apparatus,
			@RequestParam(name = APPARATUS_CONTENT, defaultValue = "") String apparatusContent,
			Model model
			) throws JsonGenerationException, JsonMappingException, IOException {

		if(medContent.isEmpty() && bloodContent.isEmpty() && apparatusContent.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, SELECT_FROM_MEDICINE_BLOOD_APPARATUS);
			return RECEIVER_FORM2_PAGE;
		}	
		if(desc.isEmpty()) {
			model.addAttribute(FORM_ERROR_LABEL, DESCRIPTION_BLANK_ERROR);
			return RECEIVER_FORM2_PAGE;
		}
		
		ReceiverRequestDetails request = new ReceiverRequestDetails();
		request.setReceiverId(LoginController.loggedInReceiver.getId());
		request.setDescription(desc);
		if(medicine.isEmpty())
			request.setMedicine(null);
		else
			request.setMedicine(medContent);
		if(blood.isEmpty())
			request.setBlood(null);
		else
			request.setBlood(bloodContent);
		if(apparatus.isEmpty())
			request.setMedical_apparatus(null);
		else
			request.setMedical_apparatus(apparatusContent);
		receiverService.addRequest(request);
		return REDIRECT_TO_MYREQUEST_PAGE;
	}
	
	@GetMapping(DELETE_REQUEST_TASK)
	public String requestDeleteTask(@RequestParam("requestId") int requestId) {
		
		receiverService.deleteRequest(requestId);
		return REDIRECT_TO_MYREQUEST_PAGE;
	}

}
