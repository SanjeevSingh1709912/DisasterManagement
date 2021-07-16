package org.nagarro.disasterhelp.contollers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nagarro.disasterhelp.constants.ControllerConstants;
import org.nagarro.disasterhelp.models.Provider;
import org.nagarro.disasterhelp.models.ProviderOfferDetails;
import org.nagarro.disasterhelp.models.Receiver;
import org.nagarro.disasterhelp.models.ReceiverRequestDetails;
import org.nagarro.disasterhelp.services.ProviderService;
import org.nagarro.disasterhelp.services.ReceiverService;
import org.nagarro.disasterhelp.services.LoginService;
import org.nagarro.disasterhelp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/DisasterHelp")
public class LoginController implements ControllerConstants {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ReceiverService receiverService;

	static protected Provider loggedInProvider = null;
	static protected Receiver loggedInReceiver = null;

	@GetMapping(LOGIN_URL)
	public String login(Model model) {
		model.addAttribute(LOGIN_ERROR_LABEL, "");
		return LOGIN_PAGE;
	}

	@GetMapping("/provider/logout")
	public String logoutTaskForProvider() {
		loggedInProvider = null;
		return "redirect:/DisasterHelp/login";
	}

	@GetMapping("/receiver/logout")
	public String logoutTaskForReceiver() {
		loggedInReceiver = null;
		return "redirect:/DisasterHelp/login";
	}

	@GetMapping(PROVIDER_SIGNUP_URL)
	public String providerSignup(Model model) {
		model.addAttribute(EMAIL_ERROR_LABEL, "");
		model.addAttribute(NEW_PROVIDER, new Provider());
		return PROVIDER_SIGNUP_PAGE;
	}

	@GetMapping(RECEIVER_SIGNUP_URL)
	public String receiverSignup(Model model) {
		model.addAttribute(EMAIL_ERROR_LABEL, "");
		model.addAttribute(NEW_RECEIVER, new Receiver());
		return RECEIVER_SIGNUP_PAGE;
	}

	@GetMapping(PROVIDER_HOME_URL)
	public String providerHome() {
		if (loggedInProvider == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		return PROVIDER_HOME_PAGE;
	}

	@GetMapping(RECEIVER_HOME_URL)
	public String receiverHome() {
		if (loggedInReceiver == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		return RECEIVER_HOME_PAGE;
	}

	@GetMapping(PROVIDER_FOMR1_URL)
	public String providerForm1(Model model) {
		if (loggedInProvider == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		model.addAllAttributes(ADDRESS);
		return PROVIDER_FORM1_PAGE;
	}

	@GetMapping(PROVIDER_FORM2_URL)
	public String providerForm2(Model model) {
		if (loggedInProvider == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		model.addAllAttributes(ADDRESS);
		return PROVIDER_FORM2_PAGE;
	}

	@GetMapping(PROVIDER_DONATIONS_URL)
	public String providerAllDonations(Model model) throws JsonParseException, JsonMappingException, IOException {
		if (loggedInProvider == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		List<ProviderOfferDetails> offersList = providerService.getOffersByProviderId(loggedInProvider.getId());
		model.addAttribute(OFFER_LIST, offersList);
		return PROVIDER_OFFERS_PAGE;
	}

	@GetMapping(RECEIVER_FORM1_URL)
	public String receiverForm1(Model model) {
		if (loggedInReceiver == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		model.addAttribute(FORM_ERROR_LABEL, "");
		return RECEIVER_FORM1_PAGE;
	}

	@GetMapping(RECEIVER_FORM2_URL)
	public String receiverForm2(Model model) {
		if (loggedInReceiver == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		model.addAttribute(FORM_ERROR_LABEL, "");
		return RECEIVER_FORM2_PAGE;
	}

	@GetMapping(RECEIVER_REQUEST_URL)
	public String receiverAllRequests(Model model) throws JsonParseException, JsonMappingException, IOException {
		if (loggedInReceiver == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		List<ReceiverRequestDetails> requestsList = receiverService.getRequestsByReceiverId(loggedInReceiver.getId());
		model.addAttribute(REQUEST_LIST, requestsList);
		return RECEIVER_REQUESTS_PAGE;
	}

	@GetMapping(PROVIDER_MATCHED_URL)
	public String matchedReceiverRequest(@RequestParam(MATCHED_REQUEST_ID) int matchedRequestId, Model model)
			throws JsonParseException, JsonMappingException, IOException {
		if (loggedInProvider == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		ReceiverRequestDetails matchedRequestDetails = receiverService.getRequestById(matchedRequestId);
		Receiver matchedReceiverDetails = receiverService.getReceiverById(matchedRequestDetails.getReceiverId());
		model.addAttribute(MATCHED_REQUEST_DETAILS, matchedRequestDetails);
		model.addAttribute(MATCHED_RECEIVER_DETAILS, matchedReceiverDetails);
		return PROVIDER_MATCHED_PAGE;
	}

	@GetMapping(RECEIVER_MATCHED_URL)
	public String matchedProviderOffers(@RequestParam(MATCHED_OFFER_ID) int matchedOfferId, Model model)
			throws JsonParseException, JsonMappingException, IOException {
		if (loggedInReceiver == null)
			return REDIRECT_TO_LOGIN_PAGE_URL;
		ProviderOfferDetails matchedOfferDetails = providerService.getOfferById(matchedOfferId);
		Provider matchedProviderDetails = providerService.getProviderById(matchedOfferDetails.getProviderId());
		model.addAttribute(MATCHED_OFFER_DETAILS, matchedOfferDetails);
		model.addAttribute(MATCHED_PROVIDER_DETAILS, matchedProviderDetails);
		return RECEIVER_MATCHED_PAGE;
	}

	@PostMapping(PROVIDER_SIGNUP_TASK)
	public String signupTaskForProvider(@Valid @ModelAttribute(NEW_PROVIDER) Provider provider, BindingResult result,
			Model model) throws JsonGenerationException, JsonMappingException, IOException {
		if (result.hasErrors()) {
			return PROVIDER_SIGNUP_PAGE;
		}
		if (providerService.signupEmailValidation(provider.getEmail())) {
			model.addAttribute(EMAIL_ERROR_LABEL, EMAIL_ERROR);
			return PROVIDER_SIGNUP_PAGE;
		}

		registrationService.addProvider(provider);
		loggedInProvider = loginService.getProviderByEamilAndPassword(provider.getEmail(), provider.getPassword());
		return REDIRECT_TO_PROVIDER_HOME_PAGE_URL;
	}

	@PostMapping(RECEIVER_SIGNUP_TASK)
	public String signupTaskForReceiver(@Valid @ModelAttribute(NEW_RECEIVER) Receiver receiver, BindingResult result,
			Model model) throws JsonGenerationException, JsonMappingException, IOException {

		if (result.hasErrors()) {
			return RECEIVER_SIGNUP_PAGE;
		}
		if (receiverService.signupEmailValidation(receiver.getEmail())) {
			model.addAttribute(EMAIL_ERROR_LABEL, EMAIL_ERROR);
			return RECEIVER_SIGNUP_PAGE;
		}

		registrationService.addReceiver(receiver);
		loggedInReceiver = loginService.getReceiverByEmailAndPassword(receiver.getEmail(), receiver.getPassword());
		return REDIRECT_TO_RECEIVER_HOME_PAGE_URL;
	}

	@PostMapping(LOGIN_TASK)
	public String loginTask(@RequestParam(EMAIL) String email, @RequestParam(PASSWORD) String password,
			@RequestParam(USER) String user, Model model) throws JsonParseException, JsonMappingException, IOException {

		if (user.equalsIgnoreCase("provider")) {
			Provider provider = loginService.getProviderByEamilAndPassword(email, password);
			if (provider != null) {
				loggedInProvider = provider;
				return REDIRECT_TO_PROVIDER_HOME_PAGE_URL;
			} else
				model.addAttribute(LOGIN_ERROR_LABEL, LOGIN_ERROR);
			return LOGIN_PAGE;
		} else {
			Receiver receiver = loginService.getReceiverByEmailAndPassword(email, password);
			if (receiver != null) {
				loggedInReceiver = receiver;
				return REDIRECT_TO_RECEIVER_HOME_PAGE_URL;
			} else
				model.addAttribute(LOGIN_ERROR_LABEL, LOGIN_ERROR);
			return LOGIN_PAGE;
		}
	}
}
