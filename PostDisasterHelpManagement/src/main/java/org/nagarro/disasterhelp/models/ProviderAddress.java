package org.nagarro.disasterhelp.models;


public class ProviderAddress {
	
	private int id;
	private String country;
	private String state;
	private String city;
	private String postal_code;
	
	public ProviderAddress() {};
	
	public ProviderAddress(String country, String state, String city, String postal_code) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.postal_code = postal_code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	
	@Override
	public String toString() {
		return "ReceiverAddress [id=" + id + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", postal_code=" + postal_code + "]";
	}
	
	

}
