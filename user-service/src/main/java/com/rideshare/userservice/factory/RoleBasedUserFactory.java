package com.rideshare.userservice.factory;

import com.rideshare.userservice.model.User;
import com.rideshare.userservice.model.HostDriver;
import com.rideshare.userservice.model.Passenger;

public interface RoleBasedUserFactory {
    HostDriver createDriver(User user);
    Passenger createPassenger(User user);
}
