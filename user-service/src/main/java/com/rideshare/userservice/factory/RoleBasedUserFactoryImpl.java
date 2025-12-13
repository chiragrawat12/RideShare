package com.rideshare.userservice.factory;

import com.rideshare.userservice.model.HostDriver;
import com.rideshare.userservice.model.Passenger;
import com.rideshare.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class RoleBasedUserFactoryImpl implements RoleBasedUserFactory {

    @Override
    public HostDriver createDriver(User user) {
        HostDriver driver = new HostDriver();
        driver.setUser(user);
        driver.setLicenseNumber("NOT_SET");
        driver.setVehicleDetails("NOT_SET");
        return driver;
    }

    @Override
    public Passenger createPassenger(User user) {
        Passenger passenger = new Passenger();
        passenger.setUser(user);
        passenger.setPreferredPaymentMethod("CARD");
        return passenger;
    }
}
