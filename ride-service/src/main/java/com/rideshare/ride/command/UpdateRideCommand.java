package com.rideshare.ride.command;

import com.rideshare.ride.entity.Ride;
import com.rideshare.ride.dto.RideRequestDTO;
import com.rideshare.ride.repository.RideRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete Command - Update Ride
 * Updates an existing ride and stores original values for undo
 */
public class UpdateRideCommand implements RideCommand {

    private RideRepository rideRepository;
    private Long rideId;
    private RideRequestDTO updatedData;
    private Ride ride;

    // Store original values for undo
    private String originalSource;
    private String originalDestination;
    private BigDecimal originalPrice;
    private LocalDateTime originalDepartureDateTime;
    private LocalDateTime originalArrivalDateTime;

    public UpdateRideCommand(RideRepository rideRepository, Long rideId, RideRequestDTO updatedData) {
        this.rideRepository = rideRepository;
        this.rideId = rideId;
        this.updatedData = updatedData;
    }

    @Override
    public void execute() {
        // Get existing ride
        ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + rideId));

        // Save original values
        this.originalSource = ride.getSource();
        this.originalDestination = ride.getDestination();
        this.originalPrice = ride.getPrice();
        this.originalDepartureDateTime = ride.getDepartureDateTime();
        this.originalArrivalDateTime = ride.getArrivalDateTime();

        // Update ride with new values
        ride.setSource(updatedData.getSource());
        ride.setDestination(updatedData.getDestination());
        ride.setPrice(updatedData.getPrice());
        ride.setDepartureDateTime(updatedData.getDepartureDateTime());
        ride.setArrivalDateTime(updatedData.getArrivalDateTime());
        ride.setUpdatedAt(LocalDateTime.now());

        // Save updated ride
        rideRepository.save(ride);

        System.out.println("✓ Command Executed: Updated ride #" + rideId);
    }

    @Override
    public void undo() {
        if (ride != null) {
            // Restore original values
            ride.setSource(originalSource);
            ride.setDestination(originalDestination);
            ride.setPrice(originalPrice);
            ride.setDepartureDateTime(originalDepartureDateTime);
            ride.setArrivalDateTime(originalArrivalDateTime);
            ride.setUpdatedAt(LocalDateTime.now());

            rideRepository.save(ride);

            System.out.println("↶ Command Undone: Ride #" + rideId + " restored to original values");
        } else {
            System.out.println("✗ Cannot undo: Ride not found");
        }
    }

    @Override
    public String getDescription() {
        return "Update ride #" + rideId + " from " + originalSource + " to " + originalDestination;
    }

    public Ride getUpdatedRide() {
        return ride;
    }
}