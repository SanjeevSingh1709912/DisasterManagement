package org.nagarro.disasterhelp.consumer.constants;

public interface Constants {
	
	public static final String GET_RECEIVER_BY_ID_API = "http://API-Gateway/receivers/";
	public static final String UPDATE_OFFER_BY_ID_API = "http://API-Gateway/offers/";
	public static final String GET_OFFER_BY_REQUEST_ID = "http://API-Gateway/offers/requestid/";

	public static final String RECEIVER_REGISTRATION_SERVICE_CIRCUIT_BREAKER = "receiverRegistrationService-cb";
	public static final String PROVIDER_OFFER_SERVICE_CIRCUIT_BREAKER = "providerOfferService-cb";
}
