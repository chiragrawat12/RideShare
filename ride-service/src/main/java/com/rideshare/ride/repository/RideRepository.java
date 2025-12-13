package com.rideshare.ride.repository;
import com.rideshare.ride.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByHostDriverId(Long hostDriverId);

    List<Ride> findByStatus(String status);

    List<Ride> findBySourceAndDestination(String source, String destination);

    @Query("SELECT r FROM Ride r WHERE r.source = :source AND r.destination = :destination " +
            "AND r.availableSeats > 0 AND r.status = 'ACTIVE'")
    List<Ride> findAvailableRides(@Param("source") String source,
                                  @Param("destination") String destination);

    @Query("SELECT r FROM Ride r WHERE r.departureDateTime > :now " +
            "AND r.status = 'ACTIVE' ORDER BY r.departureDateTime ASC")
    List<Ride> findUpcomingRides(@Param("now") LocalDateTime now);

    Optional<Ride> findById(Long id);
}
