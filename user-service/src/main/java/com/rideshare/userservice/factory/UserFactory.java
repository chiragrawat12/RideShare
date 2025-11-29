package com.rideshare.userservice.factory;
import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.HostDriver;
import com.rideshare.userservice.model.Passenger;
import com.rideshare.userservice.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public class UserFactory {

    public static Object createUser(RegisterRequest req, User user) {

        if (req.getRole().equalsIgnoreCase("PASSENGER")) {
            Passenger p = new Passenger();
            p.setUser(user);
            p.setPreferredPaymentMethod("CARD");
            return p;
        }
        else if (req.getRole().equalsIgnoreCase("DRIVER")) {
            HostDriver d = new HostDriver();
            d.setUser(user);
            d.setLicenseNumber("NOT_SET");
            d.setVehicleDetails("NOT_SET");
            return d;
        }
        return null;
    }
}

