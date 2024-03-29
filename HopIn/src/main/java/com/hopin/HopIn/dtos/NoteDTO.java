package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NoteDTO {
	
	@NotEmpty(message = "is required")
	@Size(max=500, message = "too long")
	String message;
	
	public NoteDTO() {}

	public NoteDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
