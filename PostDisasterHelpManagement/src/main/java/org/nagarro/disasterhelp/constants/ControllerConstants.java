package org.nagarro.disasterhelp.constants;

import java.util.Map;

public interface ControllerConstants {
	
	//All HTML pages constants
	public static final String LOGIN_PAGE = "login";
	
	public static final String PROVIDER_SIGNUP_PAGE = "provider_signup";
	public static final String PROVIDER_HOME_PAGE = "provider_home";
	public static final String PROVIDER_FORM1_PAGE = "provider_form1";
	public static final String PROVIDER_FORM2_PAGE = "provider_form2";
	public static final String PROVIDER_OFFERS_PAGE = "provider_offers";
	public static final String PROVIDER_MATCHED_PAGE = "provider_matched";
	
	public static final String RECEIVER_SIGNUP_PAGE = "receiver_signup";
	public static final String RECEIVER_HOME_PAGE = "receiver_home";
	public static final String RECEIVER_FORM1_PAGE = "receiver_form1";
	public static final String RECEIVER_FORM2_PAGE = "receiver_form2";
	public static final String RECEIVER_REQUESTS_PAGE = "receiver_requests";
	public static final String RECEIVER_MATCHED_PAGE = "receiver_matched";
	
	//All HTML pages mapped URLs
	public static final String LOGIN_URL = "/login";
	public static final String PROVIDER_SIGNUP_URL = "/provider/signup";
	public static final String RECEIVER_SIGNUP_URL = "/receiver/signup";	
	public static final String PROVIDER_HOME_URL = "/provider/home";
	public static final String RECEIVER_HOME_URL = "/receiver/home";
	public static final String PROVIDER_FOMR1_URL = "/provider/form1";
	public static final String PROVIDER_FORM2_URL = "/provider/form2";
	public static final String PROVIDER_DONATIONS_URL = "/provider/mydonations";
	public static final String RECEIVER_FORM1_URL = "/receiver/form1";
	public static final String RECEIVER_FORM2_URL = "/receiver/form2";
	public static final String RECEIVER_REQUEST_URL = "/receiver/myrequests";
	public static final String PROVIDER_MATCHED_URL = "/provider/matched-receiver";
	public static final String RECEIVER_MATCHED_URL = "/receiver/matched-provider";
	
	//All performed Tasks/Operations URLs
	public static final String LOGIN_TASK = "/loginTask";
	public static final String PROVIDER_SIGNUP_TASK = "/providerSignupTask";
	public static final String RECEIVER_SIGNUP_TASK = "/receiverSignupTask";
	public static final String PROVIDER_FORM1_TASK = "/providerForm1Task";
	public static final String PROVIDER_FORM2_TASK = "/providerForm2Task";
	public static final String DELETE_OFFER_TASK = "/offer/deleteOfferTask";
	public static final String RECEIVER_FORM1_TASK = "/receiverForm1Task";
	public static final String RECEIVER_FORM2_TASK = "/receiverForm2Task";
	public static final String DELETE_REQUEST_TASK = "/request/deleteRequestTask";
	
	
	//All redirect URLs
	public static final String REDIRECT_TO_LOGIN_PAGE_URL = "redirect:/DisasterHelp/login";
	public static final String REDIRECT_TO_PROVIDER_HOME_PAGE_URL = "redirect:/DisasterHelp/provider/home";
	public static final String REDIRECT_TO_RECEIVER_HOME_PAGE_URL = "redirect:/DisasterHelp/receiver/home";
	public static final String REDIRECT_TO_MYDONATION_PAGE = "redirect:/DisasterHelp/provider/mydonations";
	public static final String REDIRECT_TO_MYREQUEST_PAGE = "redirect:/DisasterHelp/receiver/myrequests";
	
	//All errors with their labels
	public static final String LOGIN_ERROR_LABEL = "loginerror";
	public static final String EMAIL_ERROR_LABEL = "emailError";
	public static final String FORM_ERROR_LABEL = "formError";
	public static final String PINCODE_ERROR_LABEL = "pincodeError";
	
	public static final String LOGIN_ERROR = "Invalid username or password";
	public static final String EMAIL_ERROR = "Email already being registered";
	public static final String DESCRIPTION_BLANK_ERROR = "Please give some description";
	public static final String INVALID_PINCODE_ERROR = "Invalid Pincode";
	public static final String SELECT_FROM_FOOD_CLOTH = "Select atleast one from food and cloth";
	public static final String SELECT_FROM_MEDICINE_BLOOD_APPARATUS = "Select atleast one from above donations";
	public static final String ADDRESS_ERROR = "Please provide full address";
	
	//Some Useful and Important variable 
	public static final String NEW_PROVIDER = "newProvider";
	public static final String NEW_RECEIVER = "newReceiver";
	public static final String OFFER_LIST = "offersList";
	public static final String REQUEST_LIST = "requestsList";
	public static final String MATCHED_REQUEST_DETAILS = "matchedRequestDetails";
	public static final String MATCHED_RECEIVER_DETAILS = "matchedReceiverDetails";
	public static final String MATCHED_OFFER_DETAILS = "matchedOfferDetails";
	public static final String MATCHED_PROVIDER_DETAILS = "matchedProviderDetails";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String USER = "user";
	public static final String MATCHED_OFFER_ID = "matched-offerId";
	public static final String MATCHED_REQUEST_ID = "matched-requestId";
	public static final Map<String, String> ADDRESS = Map.of("country","","state","","city","","pincode","","desc","","pincodeError","","formError","");

	public static final String COUNTRY = "country";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String PINCODE = "pincode";
	public static final String CLOTH = "cloth";
	public static final String FOOD = "food";
	public static final String DESCRIPTION = "description";
	public static final String MEDICINE = "medicine";
	public static final String MEDICINE_CONTENT = "medicinecontent";
	public static final String BLOOD = "blood";
	public static final String BLOOD_CONTENT = "bloodcontent";
	public static final String APPARATUS = "apparatus";
	public static final String APPARATUS_CONTENT = "apparatuscontent";
	
	
	
	
	

}
