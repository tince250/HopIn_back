package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@Service
public class PassengerServiceImpl implements IPassengerService {
	private Map<Integer, User> allPassengers= new HashMap<Integer, User>();
	private int currId = 0;
	
	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengers);
	}
	
	@Override
	public UserReturnedDTO getPassenger(int id) {
		return new UserReturnedDTO(this.allPassengers.get(id));
	}
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
		passenger.setId(++this.currId);
		this.allPassengers.put(this.currId, passenger);
		
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public boolean Activate(int id) {
		User passenger = this.allPassengers.get(id);
		if(passenger != null) {
			passenger.setActivated(true);
			return true;
		}
		return false;
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		User passenger = this.allPassengers.get(id);
		if(passenger == null) {
			return null;
		}
		passenger.copy(dto);
		return new UserReturnedDTO(passenger);
	}

}
