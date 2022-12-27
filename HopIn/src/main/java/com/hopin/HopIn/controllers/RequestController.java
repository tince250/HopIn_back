package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
import com.hopin.HopIn.services.interfaces.IAdministratorService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	IAdministratorService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAll() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAll();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pending",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllPending() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllPending();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/processed",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllProcessed() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllProcessed();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/driver/pending", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllDriverPending(@PathVariable int id) {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllDriverPending(id);
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/driver/processed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllDriverProcessed(@PathVariable int id) {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllDriverProcessed(id);
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/admin/processed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllAdminProcessed(@PathVariable int id) {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllAdminProcessed(id);
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DriverAccountUpdateRequest> getById(@PathVariable int id) {
		return new ResponseEntity<DriverAccountUpdateRequest>(this.service.getById(id), HttpStatus.OK);
	}

}
