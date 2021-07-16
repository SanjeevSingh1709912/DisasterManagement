package org.nagarro.disasterhelp.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.nagarro.disasterhelp.constants.ModelConstants;

public class ReceiverAddress implements ModelConstants {
	
	private int id;
	
	@NotBlank(message = BLANK_ERROR)
	private String country;
	
	@NotBlank(message = BLANK_ERROR)
	private String state;
	
	@NotBlank(message = BLANK_ERROR)
	private String city;
	
	@NotBlank(message = BLANK_ERROR)
	@Pattern(regexp = "^[0-9]+$", message = NUMBER_ERROR)
	@Size(min=6,max = 6,message = PINCODE_SIZE_ERROR)
	private String postal_code;
	
	
	public ReceiverAddress() {};
	
	public ReceiverAddress(String country, String state, String city, String postal_code) {
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
