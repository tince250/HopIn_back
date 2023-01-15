package com.hopin.HopIn.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideOfferEstimationDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Panic;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoActiveDriverRideException;
import com.hopin.HopIn.exceptions.NoActivePassengerRideException;
import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideEstimationService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;
import com.hopin.HopIn.util.TokenUtils;
import jakarta.validation.constraints.Min;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@Service
public class RideServiceImpl implements IRideService {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private PanicRepository allPanics;

	@Autowired
	private RideRepository allRides;

	@Autowired
	private VehicleTypeRepository allVehicleTypes;

	@Autowired
	private IDriverService driverService;
	
	@Autowired 
	private IPassengerService passengerService;
	
	@Autowired
	private IRideEstimationService rideEstimationService;
	
	@Autowired 
	private IWorkingHoursService workingHoursService;
	
	@Autowired
	private IVehicleTypeService vehicleTypeService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private Map<Integer, Ride> allRidess = new HashMap<Integer, Ride>();
	private Set<PanicRideDTO> allPanicRides = new HashSet<PanicRideDTO>();
	private int currId = 0;

	@Override
	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		List<Ride> rides = allRides.getAllPassengerRidesBetweenDates(id,
				LocalDate.parse(from, formatter).atStartOfDay(), LocalDate.parse(to, formatter).atStartOfDay());
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for (Ride ride : rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}

	public List<RideForReportDTO> getAllDriverRidesBetweenDates(int id, String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		List<Ride> rides = allRides.getAllDriverRidesBetweenDates(id, LocalDate.parse(from, formatter).atStartOfDay(),
				LocalDate.parse(to, formatter).atStartOfDay());
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for (Ride ride : rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}

//	public RideServiceImpl() {
//		List<LocationNoIdDTO> locs = new ArrayList<LocationNoIdDTO>();
//		locs.add(new LocationNoIdDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549));
//		locs.add(new LocationNoIdDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549));
//
//		List<UserInRideDTO> passengers = new ArrayList<UserInRideDTO>();
//		passengers.add(new UserInRideDTO(1, "mika@gmail.com"));
//		
//		Ride ride = new Ride(++this.currId, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), 2000.0, 5,
//			RideStatus.REJECTED, false, false, VehicleTypeName.STANDARDNO, null,
//			new RejectionNotice("Odbijaaaam!"), passengers, locs, new UserInRideDTO(1, "driver@gmail.com"));
//		
//		this.allRides.put(1, ride);
//	}

//	@Override
//	public RideReturnedDTO create(RideDTO dto) {
//		Ride ride = new Ride(dto, ++this.currId);
//		this.allRides.put(ride.getId(), ride);
//		return new RideReturnedDTO(ride);
//	}

//	@Override
//	public RideReturnedDTO getActiveRideForDriver(int id) {
//		for(Ride ride : this.allRides.values()) {
//			if(ride.getStartTime().isBefore(LocalDateTime.now()) && ride.getEndTime().isAfter(LocalDateTime.now()) 
//					&& ride.getDriver().getId() == id) {
//				return new RideReturnedDTO(ride);
//			}
//		}
//		return null;
//	}

//	@Override
//	public RideReturnedDTO getActiveRideForPassenger(int id) {
//		for(Ride ride : this.allRides.values()) {
//			if(ride.getStartTime().isBefore(LocalDateTime.now()) && ride.getEndTime().isAfter(LocalDateTime.now())) {
//				for(UserInRideDTO passenger : ride.getPassengers()) {
//					if(passenger.getId() == id) {
//						return new RideReturnedDTO(ride);
//					}
//				}
//			}
//		}
//		return null;
//	}

//	@Override
//	public RideReturnedWithRejectionDTO getRide(int id) {
//		for(int key: this.allRides.keySet()) {
//			if (key == id) {
//				return new RideReturnedWithRejectionDTO(this.allRides.get(key));
//			}
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO cancelRide(int id) {
//		for (int key : this.allRides.keySet()) {
//			if (key == id) {
//				this.allRides.get(key).setStatus(RideStatus.CANCELED);;
//				return new RideReturnedDTO(this.allRides.get(key));
//			}
//		}
//		return null;
//	}
//	
//	@Override
//	public PanicRide panicRide(int id, ReasonDTO reason) {
//		Ride ride = this.allRides.get(id);
//		if(ride != null) {
//			PanicRide panicRide = new PanicRide(new RideReturnedDTO(ride), reason.getReason());
//			this.allPanicRides.add(panicRide);
//			return panicRide;
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO changeRideStatus(int id, RideStatus status) {
//		Ride ride = this.allRides.get(id);
//		if (ride != null) {
//			ride.setStatus(status);
//			return new RideReturnedDTO(ride);
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
//		Ride ride = this.allRides.get(id);
//		if (ride != null) {
//			ride.setStatus(RideStatus.REJECTED);
//			ride.setRejectionNotice(new RejectionNotice(reason.getReason()));
//			return new RideReturnedDTO(ride);
//		}
//		return null;
//	}
//	
//	@Override
//	public AllPanicRidesDTO getAllPanicRides() {
//		return new AllPanicRidesDTO(this.allPanicRides);
//	}
//	

	@Override
	public RideReturnedDTO add(RideDTO dto){
		Driver driverForRide = new Driver();
		
		List<Driver> driversForRide = new ArrayList<Driver>();
		
		driversForRide = this.driverService.getActiveDrivers();
		if (driversForRide.size() == 0) {
			throw new NoActiveDriverException();
		}
		
		driversForRide = this.driverService.getDriversWithAppropriateVehicleForRide(driversForRide, dto);
		if (driversForRide.size() == 0) {
			throw new NoDriverWithAppropriateVehicleForRideException();
		}
		
		dto.getPassengers().forEach((UserInRideDTO passenger) -> {
			try 
			{
			if (this.getActiveRideForPassenger(passenger.getId()) != null)
				{
				throw new PassengerAlreadyInRideException();
				}
			} catch (NoActivePassengerRideException e){
				e.printStackTrace();
			}
		});
		
		int newRideDuration = this.rideEstimationService.getEstimatedTime(dto.getDepartureLocation(), dto.getDestinationLocation());
		System.out.println("newrideduration" + newRideDuration);
		
		List<Driver> driversWithNoActiveRide = this.getAllDriversWithNoActiveRide(driversForRide);
		List<Driver> driversWithActiveRide = new ArrayList<Driver>(driversForRide);
		driversWithActiveRide.removeAll(driversWithNoActiveRide);
		
		boolean availabilityOfDrivers = driversWithNoActiveRide.size() != 0 ? true : false;
		
		if (availabilityOfDrivers) {
			driverForRide = this.getBestDriver(dto, driversWithNoActiveRide, newRideDuration, availabilityOfDrivers);
		} else {
			driverForRide = this.getBestDriver(dto, driversWithActiveRide, newRideDuration, availabilityOfDrivers);
		}
		
		if (driverForRide == null)
			throw new NoAvailableDriversException();
		
		Ride wantedRide = this.createWantedRide(dto, driverForRide);
		this.simpMessagingTemplate.convertAndSend("/send/invite/driver/" + driverForRide.getId(), wantedRide);
		this.allRides.save(wantedRide);
		this.allRides.flush();
		return new RideReturnedDTO(wantedRide);
	}
	
	private Driver getBestDriver(RideDTO rideDTO, List<Driver> drivers, int newRideDuration, boolean availability) {
		Driver foundDriver = new Driver();
		int bestTime = Integer.MAX_VALUE;
		
		for (Driver driver: drivers) {
			LocalDateTime scheduledTime = rideDTO.getScheduledTime();
			if (scheduledTime != null)
				return null;
			
			LocalDateTime startOfDrivingToDeparture = LocalDateTime.now();
			
			int timeForNewRideDepartureArrival = 0;
			
			if (availability)
				timeForNewRideDepartureArrival = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), driver.getVehicleLocation());
			else {
				RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
				startOfDrivingToDeparture = currentRide.getStartTime().plusMinutes(currentRide.getEstimatedTimeInMinutes());
				timeForNewRideDepartureArrival = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), currentRide.getLocations().get(0).getDestination());
			}
			
			Ride nextRide = this.allRides.getFirstUpcomingRideForDriver(driver.getId());
			
			int timeFromStartOfNewToStartOfNext = timeForNewRideDepartureArrival + newRideDuration;
			
			int timeForUpcomingRideDepartureArrival = 0;
			if (nextRide != null) {
				timeForUpcomingRideDepartureArrival= this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), new LocationNoIdDTO(nextRide.getDepartureLocation()));
				timeFromStartOfNewToStartOfNext += timeForUpcomingRideDepartureArrival;
				
				if (startOfDrivingToDeparture.plusMinutes(timeFromStartOfNewToStartOfNext).isAfter(nextRide.getStartTime())) {
					System.out.println("ne moze ovaj driver" + driver.getId());
					continue;
				}
			}
			int totalTimeForDepartureArrival = timeForNewRideDepartureArrival;
			if (!availability)
				totalTimeForDepartureArrival += this.getMinutesUntilEndOfCurrentRideForDriver(driver);
			
			double workingHoursWithNewRide = this.workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), timeFromStartOfNewToStartOfNext);
			double workingHorusOfScheduledRides = this.getWorkingHoursOfAllScheduledRideForDay(driver.getId());
			double totalWorkingHours = workingHorusOfScheduledRides + workingHoursWithNewRide;
			
			if (!availability) 
				totalWorkingHours += this.getMinutesUntilEndOfCurrentRideForDriver(driver)/60;
			
			if (totalWorkingHours < 8 && totalTimeForDepartureArrival < bestTime) {
				foundDriver = driver;
				bestTime = (int) (totalTimeForDepartureArrival);
			}
				
		}
		return foundDriver;
	}
	

	
	private Ride createWantedRide(RideDTO rideDTO, Driver driver){
		Ride ride = new Ride();
		
		ride.setStartTime(null);
		ride.setEndTime(null);
		ride.setScheduledTime(LocalDateTime.now());
		
		ride.setPetTransport(rideDTO.isPetTransport());
		ride.setBabyTransport(rideDTO.isBabyTransport());
		ride.setPanic(false);
		
		ride.setStatus(RideStatus.PENDING);
		
		for (UserInRideDTO passenger: rideDTO.getPassengers()) {
			ride.getPassengers().add(this.passengerService.getPassenger(passenger.getId()));
		}
		ride.setDriver(driver);
		
		ride.setReviews(null);
		ride.setVehicleType(this.vehicleTypeService.getByName(rideDTO.getVehicleType()));
		ride.setDepartureLocation(new Location(rideDTO.getDepartureLocation()));
		ride.setDestinationLocation(new Location(rideDTO.getDestinationLocation()));
		ride.setRejectionNotice(null);
		
		int estimatedTimeInMinutes = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation());
		double distance = this.rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation());
		
		ride.setDistance(distance);
		ride.setTotalDistance(distance);
		ride.setTotalCost(calculatePrice(distance, rideDTO.getVehicleType().toString()));
		ride.setEstimatedTimeInMinutes(estimatedTimeInMinutes);
		
		return ride;
	}
	
	private double calculatePrice(double distance, String vehicleTypeName) {
		VehicleType vehicleType = this.allVehicleTypes
				.getByName(VehicleTypeName.valueOf(VehicleTypeName.class, vehicleTypeName));
		return vehicleType.getPricePerKm() * distance;
	}
	
	private double getMinutesUntilEndOfCurrentRideForDriver(Driver driver) {
		RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.MINUTES.between(now, currentRide.getStartTime().plusMinutes(currentRide.getEstimatedTimeInMinutes()));
	}
	
	private double getWorkingHoursOfAllScheduledRideForDay(int driverId) {
		LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
		LocalDateTime end = startOfToday.plusDays(1);
		List<Ride> scheduledRides = this.allRides.getAllScheduledRideForTodayForDriver(driverId, end);
		int minutes = 0;
		for (Ride ride: scheduledRides) {
			minutes += ride.getEstimatedTimeInMinutes();
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(minutes/60));
	}
	
	@Override
	public RideReturnedDTO getActiveRideForPassenger(int id) {
		Ride activeRide = this.allRides.getActiveRideForPassenger(id);
		if (activeRide == null)
			throw new NoActivePassengerRideException();
		return new RideReturnedDTO(activeRide);
	}

	@Override
	public RideReturnedDTO getActiveRideForDriver(int id) {
		Ride activeRide = this.allRides.getActiveRideForDriver(id);
		if (activeRide == null)
			throw new NoActiveDriverRideException();
		return new RideReturnedDTO(activeRide);
	}
	
	private List<Driver> getAllDriversWithNoActiveRide(List<Driver> drivers){
		List<Driver> availableDrivers = new ArrayList<Driver>();
		for (Driver driver: drivers) {
			try {
				if (this.getActiveRideForDriver(driver.getId()) == null) 
					availableDrivers.add(driver);	
			} catch (NoActiveDriverRideException e) {
				availableDrivers.add(driver);	
				e.printStackTrace();
			}
		}
		
		return availableDrivers;
	}

	@Override
	public RideReturnedDTO getRide(int id) {
		Ride ride = this.allRides.findById(id).orElse(null);
		if (ride == null)
			throw new RideNotFoundException();
		return new RideReturnedDTO(ride);
	}

	private Ride getRideIfExists(int id) {
		return this.allRides.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride does not exist!"));
	}

	private Ride changeRideStatus(Ride ride, RideStatus status) {
		ride.setStatus(status);
		Ride savedRide = this.allRides.save(ride);
		this.allRides.flush();

		return savedRide;
	}

	@Override
	public RideReturnedDTO cancelRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING && ride.getStatus() != RideStatus.STARTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot cancel a ride that is not in status PENDING or STARTED!");
		}

		Ride savedRide = this.changeRideStatus(ride, RideStatus.CANCELED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public PanicRideDTO panicRide(int id, ReasonDTO reason) {
		Ride ride = this.allRides.findById(id).orElse(null);

		if (ride == null) {
			return null;
		}

		// TODO: sta je ovo, dodati usera ulogovanog umesto null
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getName());

		Panic panic = new Panic(LocalDateTime.now(), reason.getReason(), null, ride);
		this.allPanics.save(panic);
		this.allPanics.flush();

		return new PanicRideDTO(panic);
	}

	@Override
	public RideReturnedDTO startRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.ACCEPTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot start a ride that is not in status ACCEPTED!");
		}

		ride.setStartTime(LocalDateTime.now());
		Ride savedRide = this.changeRideStatus(ride, RideStatus.STARTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO acceptRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot accept a ride that is not in status PENDING!");
		}

		Ride savedRide = this.changeRideStatus(ride, RideStatus.ACCEPTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO finishRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.STARTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot end a ride that is not in status STARTED!");
		}

		ride.setEndTime(LocalDateTime.now());
		Ride savedRide = this.changeRideStatus(ride, RideStatus.FINISHED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING && ride.getStatus() != RideStatus.ACCEPTED) {
			//MOZE LI DA SE PROMENI PORUKA OVDE, NEMA RAZLIKE IZMEDJU CANCEL PUTNIKA I REJECT VOZACA OVAKO, BUDE ZBUNJUJUCE?
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
		}

		ride.setRejectionNotice(new RejectionNotice(reason.getReason()));
		Ride savedRide = this.changeRideStatus(ride, RideStatus.REJECTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public AllPanicRidesDTO getAllPanicRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllPassengerRidesDTO getAllPassengerRides(int id, int page, int size, String sort, String from, String to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto) {
		VehicleType vehicleType = this.allVehicleTypes
				.getByName(VehicleTypeName.valueOf(VehicleTypeName.class, dto.getVehicleTypeName()));
		return vehicleType.getPricePerKm() * dto.getDistance();
	}

}
