package com.rideshare.ride.command;

import com.rideshare.ride.entity.Ride;
import com.rideshare.ride.dto.RideRequestDTO;
import com.rideshare.ride.repository.RideRepository;
import java.time.LocalDateTime;

/**
 * Concrete Command - Create Ride
 * Creates a new ride and stores it for undo capability
 */
public class CreateRideCommand implements RideCommand {

    private RideRepository rideRepository;
    private RideRequestDTO requestDTO;
    private Long hostDriverId;
    private Ride createdRide;

    public CreateRideCommand(RideRepository rideRepository, RideRequestDTO requestDTO, Long hostDriverId) {
        this.rideRepository = rideRepository;
        this.requestDTO = requestDTO;
        this.hostDriverId = hostDriverId;
    }

    @Override
    public void execute() {
        // Create new Ride from DTO
        Ride ride = new Ride(
                requestDTO.getSource(),
                requestDTO.getDestination(),
                requestDTO.getDepartureDateTime(),
                requestDTO.getArrivalDateTime(),
                requestDTO.getPrice(),
                requestDTO.getTotalSeats(),
                hostDriverId
        );

        // Set automatic fields
        ride.setAvailableSeats(requestDTO.getTotalSeats());
        ride.setStatus("ACTIVE");
        ride.setCreatedAt(LocalDateTime.now());
        ride.setUpdatedAt(LocalDateTime.now());

        // Save to database
        this.createdRide = rideRepository.save(ride);

        System.out.println("✓ Command Executed: Created ride #" + createdRide.getId()
                + " from " + requestDTO.getSource() + " to " + requestDTO.getDestination());
    }

    @Override
    public void undo() {
        if (createdRide != null && createdRide.getId() != null) {
            rideRepository.deleteById(createdRide.getId());
            System.out.println("↶ Command Undone: Deleted ride #" + createdRide.getId());
        } else {
            System.out.println("✗ Cannot undo: Ride not found");
        }
    }

    @Override
    public String getDescription() {
        return "Create ride from " + requestDTO.getSource() + " to " + requestDTO.getDestination();
    }

    /**
     * Get the created ride for response
     */
    public Ride getCreatedRide() {
        return createdRide;
    }
}