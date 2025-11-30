package com.rideshare.ride.decorator;

import com.rideshare.ride.entity.Ride;
import java.math.BigDecimal;
public class PremiumRideDecorator extends RideDecorator {

    private static final BigDecimal PREMIUM_DISCOUNT = BigDecimal.valueOf(0.9); // 10% off

    public PremiumRideDecorator(Ride ride) {
        super(ride);
    }

    @Override
    public BigDecimal getPrice() {
        // Use AVAILABLE seats (dynamic), not total seats
        BigDecimal totalPrice = ride.getPrice()
                .multiply(BigDecimal.valueOf(ride.getAvailableSeats()));

        BigDecimal discountedPrice = totalPrice.multiply(PREMIUM_DISCOUNT);

        System.out.println("Premium Ride Pricing:");
        System.out.println("Base price per seat: €" + ride.getPrice());
        System.out.println("Available seats: " + ride.getAvailableSeats());
        System.out.println("Full price: €" + totalPrice);
        System.out.println("   With 10% discount: €" + discountedPrice);

        return discountedPrice;
    }
    @Override
    public Integer getBookableSeats() {
        // Premium passenger books ALL AVAILABLE seats (dynamic)
        System.out.println("Premium booking: All " + ride.getAvailableSeats() + " available seats reserved");
        return ride.getAvailableSeats();
    }
    @Override
    public String getDescription() {
        return "PREMIUM - Books entire car - 10% discount - €" + getPrice();
    }
}