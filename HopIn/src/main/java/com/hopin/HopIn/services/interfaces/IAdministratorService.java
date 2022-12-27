package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;

public interface IAdministratorService {
	
	public List<DriverAccountUpdateRequestDTO> getAll();
	
	public List<DriverAccountUpdateRequestDTO> getAllPending();
	
	public List<DriverAccountUpdateRequestDTO> getAllProcessed();

	List<DriverAccountUpdateRequestDTO> getAllDriverProcessed(int id);

	List<DriverAccountUpdateRequestDTO> getAllDriverPending(int id);

	List<DriverAccountUpdateRequestDTO> getAllAdminProcessed(int id);

	DriverAccountUpdateRequest getById(int id);
	

}
