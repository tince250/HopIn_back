package com.hopin.HopIn.entities;

import com.hopin.HopIn.enums.RequestStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "driver_account_update_requests")
@Inheritance(strategy = InheritanceType.JOINED)
public class DriverAccountUpdateRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@NotNull
	private RequestStatus status;
	
	@NotEmpty
	private String reason;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@NotNull
	private Driver driver;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@NotNull
	private User admin;
	
	public DriverAccountUpdateRequest() {}

	public DriverAccountUpdateRequest(RequestStatus status, String reason, Driver driver, User admin) {
		super();
		this.status = status;
		this.reason = reason;
		this.driver = driver;
		this.admin = admin;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

}
