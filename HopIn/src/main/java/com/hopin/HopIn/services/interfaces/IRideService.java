package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
<<<<<<< HEAD
import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;
=======
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
>>>>>>> 80f56bd09ca07586c0a5bcdc4bda6005cab1d212
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.enums.RideStatus;

public interface IRideService {

	public RideReturnedDTO add(RideDTO dto);

	public RideReturnedDTO getActiveRideForDriver(int id);

	public RideReturnedDTO getActiveRideForPassenger(int id);

	public RideReturnedDTO getRide(int id);

	public RideReturnedDTO cancelRide(int id);

	public PanicRideDTO panicRide(int id, ReasonDTO reason);

	public RideReturnedDTO changeRideStatus(int id, RideStatus status);

	public RideReturnedDTO rejectRide(int id, ReasonDTO reason);

	public AllPanicRidesDTO getAllPanicRides();

	public AllPassengerRidesDTO getAllPassengerRides(int id, int page, int size, String sort, String from, String to);

	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to);

	public List<RideForReportDTO> getAllDriverRidesBetweenDates(int id, String from, String to);

	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto);

	public FavoriteRideReturnedDTO insertFavoriteRide(FavoriteRideDTO dto);

	public List<FavoriteRideReturnedDTO> getFavoriteRides();

	public void deleteFavoriteRide(int id);
	public RideReturnedDTO startRide(int id);

	public RideReturnedDTO acceptRide(int id);

	public RideReturnedDTO finishRide(int id);

	public AllPassengerRidesDTO getAllDriverRides(int driverId, int page, int size, String sort, String from,
			String to);

}
