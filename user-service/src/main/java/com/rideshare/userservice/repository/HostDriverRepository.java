package com.rideshare.userservice.repository;

import com.rideshare.userservice.model.HostDriver;
import com.rideshare.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HostDriverRepository extends JpaRepository<HostDriver, Long> {
    boolean existsByUser(User user);
}

