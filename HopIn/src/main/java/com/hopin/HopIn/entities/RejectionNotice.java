package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

public class RejectionNotice {
	private int id;
	private LocalDateTime timeOfRejection;
	private String reason;
	
	public RejectionNotice(String reason) {
		this.id = 1;
		this.timeOfRejection = LocalDateTime.now();
		this.reason = reason;
	}

	public LocalDateTime getTimeOfRejection() {
		return timeOfRejection;
	}

	public void setTimeOfRejection(LocalDateTime timeOfRejection) {
		this.timeOfRejection = timeOfRejection;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

}
