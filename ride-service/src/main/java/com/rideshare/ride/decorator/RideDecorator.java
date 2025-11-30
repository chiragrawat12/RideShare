package com.rideshare.ride.decorator;

import com.rideshare.ride.entity.Ride;
import java.math.BigDecimal;

/**
 * Abstract Decorator for Ride
 * Base class for ride decorators
 */
public abstract class RideDecorator {

    protected Ride ride;

    public RideDecorator(Ride ride) {
        this.ride = ride;
    }

    // Methods to decorate
    public abstract BigDecimal getPrice();

    public abstract Integer getBookableSeats();

    public abstract String getDescription();

    // Pass-through methods (not decorated)
    public Long getId() {
        return ride.getId();
    }

    public String getSource() {
        return ride.getSource();
    }

    public String getDestination() {
        return ride.getDestination();
    }

    public Integer getTotalSeats() {
        return ride.getTotalSeats();
    }

    public Integer getAvailableSeats() {
        return ride.getAvailableSeats();
    }

    public Long getHostDriverId() {
        return ride.getHostDriverId();
    }

    public String getStatus() {
        return ride.getStatus();
    }

    public Ride getBaseRide() {
        return ride;
    }
}