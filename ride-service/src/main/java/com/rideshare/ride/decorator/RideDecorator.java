package com.rideshare.ride.decorator;

import com.rideshare.ride.entity.Ride;
import java.math.BigDecimal;

/**
 * Abstract Decorator for Ride
 * Base class for ride decorators implementing Decorator Pattern
 */
public abstract class RideDecorator implements DecoratorChain {

    protected Ride ride;
    protected RideDecorator nextDecorator;

    public RideDecorator(Ride ride) {
        this.ride = ride;
        this.nextDecorator = null;
    }

    /**
     * Chain another decorator
     */
    @Override
    public RideDecorator chain(RideDecorator decorator) {
        this.nextDecorator = decorator;
        System.out.println("Chained decorator: " + this.getClass().getSimpleName() + " → " + decorator.getClass().getSimpleName());
        return decorator;
    }

    /**
     * Get next decorator in chain
     */
    public RideDecorator getNextDecorator() {
        return nextDecorator;
    }

    // Abstract methods to override
    public abstract BigDecimal getPrice();
    public abstract Integer getBookableSeats();
    public abstract String getDescription();
    public abstract String getDecoratorName();

    // Pass-through methods (delegate to wrapped ride)
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