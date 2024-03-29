package com.hopin.HopIn.services.interfaces;

import java.time.LocalDateTime;

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;

public interface IWorkingHoursService {

	WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto);

	WorkingHoursDTO updateWorkingHours(int id, WorkingHoursEndDTO dto);

	double getWorkedHoursForToday(int driverId, LocalDateTime end);
	
	double getWorkedHoursForTodayWithNewRide(int driverId, int rideMinutes);
	

	WorkingHoursDTO getWorkingHoursById(int id);

	AllHoursDTO getAllHours(int id, int page, int size);

}
