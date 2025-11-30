package com.rideshare.ride.service;

import com.rideshare.ride.entity.Ride;
import com.rideshare.ride.dto.RideRequestDTO;
import com.rideshare.ride.dto.RideResponseDTO;
import com.rideshare.ride.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rideshare.ride.command.RideCommand;
import com.rideshare.ride.command.RideCommandInvoker;
import com.rideshare.ride.command.CreateRideCommand;
import com.rideshare.ride.command.UpdateRideCommand;
import com.rideshare.ride.command.CancelRideCommand;
import com.rideshare.ride.decorator.RideDecorator;
import com.rideshare.ride.decorator.PremiumRideDecorator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideCommandInvoker commandInvoker;

    public RideResponseDTO createRide(RideRequestDTO requestDTO, Long hostDriverId) {
        Ride ride = new Ride(
                requestDTO.getSource(),
                requestDTO.getDestination(),
                requestDTO.getDepartureDateTime(),
                requestDTO.getArrivalDateTime(),
                requestDTO.getPrice(),
                requestDTO.getTotalSeats(),
                hostDriverId
        );

        Ride savedRide = rideRepository.save(ride);
        return convertToDTO(savedRide);
    }

    public List<RideResponseDTO> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RideResponseDTO getRideById(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + id));
        return convertToDTO(ride);
    }

    public List<RideResponseDTO> getRidesByDriver(Long hostDriverId) {
        List<Ride> rides = rideRepository.findByHostDriverId(hostDriverId);
        return rides.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RideResponseDTO> searchAvailableRides(String source, String destination) {
        List<Ride> rides = rideRepository.findAvailableRides(source, destination);
        return rides.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RideResponseDTO> getUpcomingRides() {
        List<Ride> rides = rideRepository.findUpcomingRides(LocalDateTime.now());
        return rides.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RideResponseDTO updateRide(Long id, RideRequestDTO requestDTO) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + id));

        ride.setSource(requestDTO.getSource());
        ride.setDestination(requestDTO.getDestination());
        ride.setDepartureDateTime(requestDTO.getDepartureDateTime());
        ride.setArrivalDateTime(requestDTO.getArrivalDateTime());
        ride.setPrice(requestDTO.getPrice());
        ride.setUpdatedAt(LocalDateTime.now());

        Ride updatedRide = rideRepository.save(ride);
        return convertToDTO(updatedRide);
    }

    public void deleteRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + id));
        rideRepository.delete(ride);
    }

    public RideResponseDTO cancelRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + id));

        ride.setStatus("CANCELLED");
        ride.setUpdatedAt(LocalDateTime.now());

        Ride updatedRide = rideRepository.save(ride);
        return convertToDTO(updatedRide);
    }

    public RideRepository getRideRepository() {
        return rideRepository;
    }

    public RideResponseDTO convertToDTO(Ride ride) {
        RideResponseDTO dto = new RideResponseDTO();
        dto.setId(ride.getId());
        dto.setSource(ride.getSource());
        dto.setDestination(ride.getDestination());
        dto.setDepartureDateTime(ride.getDepartureDateTime());
        dto.setArrivalDateTime(ride.getArrivalDateTime());
        dto.setPrice(ride.getPrice());
        dto.setTotalSeats(ride.getTotalSeats());
        dto.setAvailableSeats(ride.getAvailableSeats());
        dto.setHostDriverId(ride.getHostDriverId());
        dto.setStatus(ride.getStatus());
        dto.setCreatedAt(ride.getCreatedAt());
        return dto;
    }

    public RideResponseDTO createRideWithCommand(RideRequestDTO requestDTO, Long hostDriverId) {
        CreateRideCommand command = new CreateRideCommand(rideRepository, requestDTO, hostDriverId);
        commandInvoker.executeCommand(command);

        Ride createdRide = command.getCreatedRide();
        return convertToDTO(createdRide);
    }

    public RideResponseDTO updateRideWithCommand(Long id, RideRequestDTO requestDTO) {
        UpdateRideCommand command = new UpdateRideCommand(rideRepository, id, requestDTO);
        commandInvoker.executeCommand(command);

        Ride updatedRide = command.getUpdatedRide();
        return convertToDTO(updatedRide);
    }

    public RideResponseDTO cancelRideWithCommand(Long id) {
        CancelRideCommand command = new CancelRideCommand(rideRepository, id);
        commandInvoker.executeCommand(command);

        Ride cancelledRide = command.getCancelledRide();
        return convertToDTO(cancelledRide);
    }

    public void undoLastOperation() {
        commandInvoker.undoLastCommand();
    }

    public List<Object> searchAvailableRidesWithPremium(String source, String destination, boolean isPremium) {
        // Get regular rides
        List<Ride> availableRides = rideRepository.findAvailableRides(source, destination);

        // If premium, decorate them. If not, convert to DTO
        if (isPremium) {
            System.out.println("\n💎 PREMIUM SEARCH MODE ACTIVATED");
            return availableRides.stream()
                    .map(ride -> new PremiumRideDecorator(ride))
                    .collect(Collectors.toList());
        } else {
            // Regular search
            return availableRides.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

}