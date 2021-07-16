package org.nagarro.disasterhelp.registration.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReceiverAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String country;
	private String state;
	private String city;
	private long postal_code;
	
	
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
	public long getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(long postal_code) {
		this.postal_code = postal_code;
	}
	
	@Override
	public String toString() {
		return "ReceiverAddress [id=" + id + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", postal_code=" + postal_code + "]";
	}
	
	

}
