package org.nagarro.disasterhelp.constants;

public interface ServiceConstants {
	
	public static final String GET_PROVIDER_API = "http://localhost:9054/providers/";
	public static final String GET_PROVIDER_BY_EMAIL_API = "http://localhost:9054/providers/email/";
	public static final String ADD_PROVIDER_API = "http://localhost:9054/providers";

	public static final String GET_RECEIVER_API = "http://localhost:9054/receivers/";
	public static final String GET_RECEIVER_BY_EMAIL = "http://localhost:9054/receivers/email/";
	public static final String ADD_RECEIVER_API = "http://localhost:9054/receivers";
	
	public static final String ADD_OFFER_API = "http://localhost:9054/offers";
	public static final String GET_OFFER_BY_PROVIDER_ID_API = "http://localhost:9054/offers/provider/";
	public static final String GET_OFFER_BY_ID_API = "http://localhost:9054/offers/";
	public static final String DELETE_OFFER_API = "http://localhost:9054/offers/";
	
	public static final String ADD_REQUEST_API = "http://localhost:9054/requests";
	public static final String GET_REQUEST_BY_RECEIVER_ID_API = "http://localhost:9054/requests/receiver/";
	public static final String GET_REQUEST_BY_ID_API = "http://localhost:9054/requests/";
	public static final String DELETE_REQUEST_API = "http://localhost:9054/requests/";
	
	public static final String APPLICATION_CONTENT = "application/json";

}
