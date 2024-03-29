package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.User;

import jakarta.validation.constraints.NotEmpty;

public class UserDTOOld {
	@NotEmpty(message="is required")
	private String name;
	private String surname;
	private String email;
	private String password;
	private String newPassword;
	private String address;
	private String telephoneNumber;
	private String profilePicture;

	public UserDTOOld(String name, String surname, String email, String password, String newPassword, String address, String telephoneNumber,
			String profilePicture) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
		this.newPassword = newPassword;
	}
	
//	public UserDTO(User user) {
//		super();
//		this.name = user.getName();
//		this.surname = user.getSurname();
//		this.email = user.getEmail();
//		this.password = user.getPassword();
//		this.newPassword = user.getNewPassword();
//		this.address = user.getAddress();
//		this.telephoneNumber = user.getTelephoneNumber();
//		this.profilePicture = user.getProfilePicture();
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String phone) {
		this.telephoneNumber = phone;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}		
}
