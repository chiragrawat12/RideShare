package com.rideshare.ride.command;

import com.rideshare.ride.entity.Ride;
import com.rideshare.ride.repository.RideRepository;
import java.time.LocalDateTime;

/**
 * Concrete Command - Cancel Ride
 * Cancels a ride and stores original status for undo
 */
public class CancelRideCommand implements RideCommand {

    private RideRepository rideRepository;
    private Long rideId;
    private Ride ride;
    private String previousStatus;

    public CancelRideCommand(RideRepository rideRepository, Long rideId) {
        this.rideRepository = rideRepository;
        this.rideId = rideId;
    }

    @Override
    public void execute() {
        // Get ride
        ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + rideId));

        // Save previous status
        this.previousStatus = ride.getStatus();

        // Cancel the ride
        ride.setStatus("CANCELLED");
        ride.setUpdatedAt(LocalDateTime.now());

        rideRepository.save(ride);

        System.out.println("✓ Command Executed: Cancelled ride #" + rideId + " (Status: " + previousStatus + " → CANCELLED)");
    }

    @Override
    public void undo() {
        if (ride != null) {
            // Restore previous status
            ride.setStatus(previousStatus);
            ride.setUpdatedAt(LocalDateTime.now());

            rideRepository.save(ride);

            System.out.println("↶ Command Undone: Ride #" + rideId + " status restored to " + previousStatus);
        } else {
            System.out.println("✗ Cannot undo: Ride not found");
        }
    }

    @Override
    public String getDescription() {
        return "Cancel ride #" + rideId;
    }

    public Ride getCancelledRide() {
        return ride;
    }
}