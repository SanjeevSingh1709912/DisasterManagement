package org.nagarro.disasterhelp.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.nagarro.disasterhelp.constants.ModelConstants;

public class Provider implements ModelConstants {
	
	private int id;
	
	@NotBlank(message = BLANK_ERROR)
	private String fullname;
	
	@NotBlank(message = BLANK_ERROR)
	@Email(message = EMAIL_INVALID_ERROR)
	private String email;
	
	@NotBlank(message = BLANK_ERROR)
	@Size(min=5,message = PASSWORD_SIZE_EXCEED_ERROR)
	private String password;
	
	@NotBlank(message = BLANK_ERROR)
	@Size(min=10,max = 10,message = MOBILE_NUMBER_SIZE_ERROR)
	@Pattern(regexp = "^[0-9]+$", message = NUMBER_ERROR)
	private String mob;

	
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
		return "Provider [id=" + id + ", fullname=" + fullname + ", email=" + email
				+ ", password=" + password + ", mob=" + mob + "]";
	}
	
}
