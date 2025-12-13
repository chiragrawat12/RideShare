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
            List<Ride> availableRides = rideRepository.findAvailableRides(source, destination);

            if (!isPremium) {
                return availableRides.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }

            // Apply decorator chain
            System.out.println("\nDECORATOR CHAIN - APPLYING DECORATORS");
            System.out.println("=".repeat(80));

            return availableRides.stream()
                    .map(ride -> {
                        // Create decorator chain
                        RideDecorator decorator = new PremiumRideDecorator(ride);
                        // Can chain more decorators here if needed:
                        // decorator.chain(new VIPRideDecorator(ride));

                        System.out.println("Decorated ride #" + ride.getId());

                        // Convert to DTO with decorated values
                        RideResponseDTO dto = convertToDTO(ride);
                        dto.setPremiumPrice(decorator.getPrice());
                        dto.setBookableSeats(decorator.getBookableSeats());
                        dto.setDescription(decorator.getDescription());

                        return dto;
                    })
                    .collect(Collectors.toList());
        }
}