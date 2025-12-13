package com.rideshare.ride.decorator;

import com.rideshare.ride.entity.Ride;
import java.math.BigDecimal;

/**
 * Concrete Decorator - Premium Ride
 * Adds premium features without modifying original Ride class
 */
public class PremiumRideDecorator extends RideDecorator {

    private static final BigDecimal PREMIUM_DISCOUNT = BigDecimal.valueOf(0.9);

    public PremiumRideDecorator(Ride ride) {
        super(ride);
    }

    @Override
    public BigDecimal getPrice() {
        // Calculate: availableSeats × basePrice × 0.9 (10% discount)
        BigDecimal basePrice = ride.getPrice();
        BigDecimal availableSeatsDecimal = BigDecimal.valueOf(ride.getAvailableSeats());

        BigDecimal totalPrice = basePrice.multiply(availableSeatsDecimal);
        BigDecimal discountedPrice = totalPrice.multiply(PREMIUM_DISCOUNT);

        System.out.println("Premium Ride Pricing:");
        System.out.println("   Base price per seat: $" + basePrice);
        System.out.println("   Available seats: " + ride.getAvailableSeats());
        System.out.println("   Full car price: $" + totalPrice);
        System.out.println("   With 10% discount: $" + discountedPrice);

        // Apply next decorator in chain if exists
        if (nextDecorator != null) {
            return nextDecorator.getPrice();
        }

        return discountedPrice;
    }

    @Override
    public Integer getBookableSeats() {
        // Book ALL available seats
        System.out.println("Premium booking: All " + ride.getAvailableSeats() + " seats reserved");

        // Apply next decorator in chain if exists
        if (nextDecorator != null) {
            return nextDecorator.getBookableSeats();
        }

        return ride.getAvailableSeats();
    }

    @Override
    public String getDescription() {
        String desc = "PREMIUM - Books entire car - 10% discount - $" + getPrice();

        // Include next decorator's description in chain
        if (nextDecorator != null) {
            return desc + " | " + nextDecorator.getDescription();
        }

        return desc;
    }

    @Override
    public String getDecoratorName() {
        return "PremiumRideDecorator";
    }
}