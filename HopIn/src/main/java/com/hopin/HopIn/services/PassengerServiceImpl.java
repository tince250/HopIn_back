package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RouteRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@Service
public class PassengerServiceImpl implements IPassengerService {
	
	@Autowired
	private PassengerRepository allPassengers;
	
	@Autowired RouteRepository allRoutes;
	
	private Map<Integer, User> allPassengerss= new HashMap<Integer, User>();
	private int currId = 0;
	
	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengers.findAll());
	}
	
	@Override
	public UserReturnedDTO getPassenger(int id) {
		Optional<Passenger> found = allPassengers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new UserReturnedDTO(found.get());
	}
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
		Route route = new Route(1, null, null, 20.0);
		allRoutes.save(route);
		allRoutes.flush();
		passenger.getFavouriteRoutes().add(route);
		allPassengers.save(passenger);
		allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public List<Route> getFavouriteRoutes(int id) {
		return allPassengers.findAllRoutesById(id);
	}
	
	
	
	@Override
	public boolean Activate(int id) {
		User passenger = this.allPassengerss.get(id);
		if(passenger != null) {
			passenger.setActivated(true);
			return true;
		}
		return false;
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		User passenger = this.allPassengerss.get(id);
		if(passenger == null) {
			return null;
		}
		passenger.copy(dto);
		return new UserReturnedDTO(passenger);
	}

}
