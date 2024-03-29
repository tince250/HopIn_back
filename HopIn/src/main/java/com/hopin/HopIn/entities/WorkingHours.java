package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

import com.hopin.HopIn.dtos.WorkingHoursStartDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "working_hours")
public class WorkingHours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime start;
	private LocalDateTime end;
	private int driverId;
	
	public WorkingHours() {}

	public WorkingHours(int id, LocalDateTime start, LocalDateTime end, int driverId) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.driverId = driverId;
	}
	
	public WorkingHours(int driverId, WorkingHoursStartDTO dto) {
		super();
		this.start = dto.getStart();
		this.end = null;
		this.driverId = driverId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

}
