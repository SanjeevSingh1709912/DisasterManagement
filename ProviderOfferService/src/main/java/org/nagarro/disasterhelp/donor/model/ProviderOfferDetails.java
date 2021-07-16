package org.nagarro.disasterhelp.donor.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ProviderOfferDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String description;
	private String cloth;
	private String food;
	private String blood;
	private String medical_apparatus;
	private String medicine;
	private String others;
	private int providerId;
	private int requestId;
	private String date = LocalDate.now().toString();
	
	

	@OneToOne(targetEntity = ProviderAddress.class, cascade = CascadeType.ALL)
	private ProviderAddress providerAddress;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCloth() {
		return cloth;
	}

	public void setCloth(String cloth) {
		this.cloth = cloth;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getMedical_apparatus() {
		return medical_apparatus;
	}

	public void setMedical_apparatus(String medical_apparatus) {
		this.medical_apparatus = medical_apparatus;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public ProviderAddress getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(ProviderAddress providerAddress) {
		this.providerAddress = providerAddress;
	}

	@Override
	public String toString() {
		return "ProviderDonateOffers [id=" + id + ", description=" + description + ", cloth=" + cloth + ", food=" + food
				+ ", blood=" + blood + ", medical_apparatus=" + medical_apparatus + ", medicine=" + medicine
				+ ", others=" + others + ", providerId=" + providerId + ", requestId=" + requestId + ", date=" + date
				+ ", providerAddress=" + providerAddress + "]";
	}

		
}
