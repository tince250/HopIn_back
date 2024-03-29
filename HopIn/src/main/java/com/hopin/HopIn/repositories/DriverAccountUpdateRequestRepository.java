package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.dtos.RequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;

public interface DriverAccountUpdateRequestRepository extends JpaRepository<DriverAccountUpdateRequest, Integer>{
	
	@Query("select r from DriverAccountUpdateRequest r where r.status = 1")
	public List<DriverAccountUpdateRequest> findAllPending();
	
	@Query("select r from DriverAccountUpdateRequest r where r.status = 1 and r.driver.id = :id")
	public List<DriverAccountUpdateRequest> findAllDriverPending(int id);

	@Query("select r from DriverAccountUpdateRequest r where r.status != 1")
	public List<DriverAccountUpdateRequest> findAllProcessed();
	
	@Query("select r from DriverAccountUpdateRequest r where r.status != 1 and r.driver.id = :id")
	public List<DriverAccountUpdateRequest> findAllDriverProcessed(int id);
	
	@Query("select r from DriverAccountUpdateRequest r where r.status != 1 and r.admin.id = :id")
	public List<DriverAccountUpdateRequest> findAllAdminProcessed(int id);

}
