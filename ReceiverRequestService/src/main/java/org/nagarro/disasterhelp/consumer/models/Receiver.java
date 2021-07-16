package org.nagarro.disasterhelp.consumer.models;


public class Receiver {
	
	private int id;
	private String fullname;
	private String email;
	private String password;
	private String mob;
	private ReceiverAddress receiverAddress;	
	
	public ReceiverAddress getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(ReceiverAddress receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	@Override
	public String toString() {
		return "Receiver [id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password
				+ ", mob=" + mob + ", receiverAddress=" + receiverAddress + "]";
	}
	
}
