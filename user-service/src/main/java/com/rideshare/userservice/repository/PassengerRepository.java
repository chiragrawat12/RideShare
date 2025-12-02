package com.rideshare.userservice.repository;

import com.rideshare.userservice.model.Passenger;
import com.rideshare.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByUser(User user);
}
