package org.nagarro.disasterhelp.models;

import java.time.LocalDate;

public class ReceiverRequestDetails {
	
	private int id;
	private String description;
	private String cloth;
	private String food;
	private String blood;
	private String medical_apparatus;
	private String medicine;
	private String others;
	private int receiverId;
	private int offerId;
	private String date = LocalDate.now().toString();
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
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

	@Override
	public String toString() {
		return "ReceiverRequestDetails [id=" + id + ", description=" + description + ", cloth=" + cloth + ", food="
				+ food + ", blood=" + blood + ", medical_apparatus=" + medical_apparatus + ", medicine=" + medicine
				+ ", others=" + others + ", receiverId=" + receiverId + ", offerId=" + offerId +
				 ", date=" + date + "]";
	}
}
