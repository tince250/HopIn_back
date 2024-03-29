package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.VehicleTypeName;

public class VehicleRequestDTO {

	private int id;
	private String model;
	private String licenseNumber;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;
	private VehicleTypeName vehicleType;
	private RequestStatus status;

	public VehicleRequestDTO() {

	}

	public VehicleRequestDTO(DriverAccountUpdateVehicleRequest request) {
		this.id = request.getId();
		this.model = request.getModel();
		this.licenseNumber = request.getLicenseNumber();
		this.passengerSeats = request.getPassengerSeats();
		this.babyTransport = request.isBabyTransport();
		this.petTransport = request.isPetTransport();
		this.vehicleType = request.getVehicleType().getName();
		this.status = request.getStatus();
	}

	public VehicleRequestDTO(int id, String model, String licenseNumber, int passengerSeats,
			boolean babyTransport, boolean petTransport, RequestStatus status) {
		super();
		this.id = id;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public int getPassengerSeats() {
		return passengerSeats;
	}

	public void setPassengerSeats(int passengerSeats) {
		this.passengerSeats = passengerSeats;
	}

	public boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
		this.vehicleType = vehicleType;
	}
}
