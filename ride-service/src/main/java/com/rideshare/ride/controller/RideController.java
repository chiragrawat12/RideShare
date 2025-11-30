package com.rideshare.ride.controller;

import com.rideshare.ride.entity.Ride;
import com.rideshare.ride.dto.RideRequestDTO;
import com.rideshare.ride.dto.RideResponseDTO;
import com.rideshare.ride.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@CrossOrigin(origins = "*")
public class RideController {

    @Autowired
    private RideService rideService;

    /**
     * POST /api/v1/rides/create
     * Create a new ride - supports both regular and command-based
     *
     * Usage:
     * POST /api/v1/rides/create (regular)
     * POST /api/v1/rides/create?useCommand=true (with undo capability)
     */
    @PostMapping("/create")
    public ResponseEntity<RideResponseDTO> createRide(
            @RequestBody RideRequestDTO requestDTO,
            @RequestHeader("X-User-Id") Long hostDriverId,
            @RequestParam(defaultValue = "false", required = false) boolean useCommand) {

        RideResponseDTO responseDTO;

        if (useCommand) {
            // Use Command Pattern (has undo capability)
            responseDTO = rideService.createRideWithCommand(requestDTO, hostDriverId);
        } else {
            // Use regular method (no undo)
            Ride ride = new Ride(
                    requestDTO.getSource(),
                    requestDTO.getDestination(),
                    requestDTO.getDepartureDateTime(),
                    requestDTO.getArrivalDateTime(),
                    requestDTO.getPrice(),
                    requestDTO.getTotalSeats(),
                    hostDriverId
            );
            ride.setAvailableSeats(requestDTO.getTotalSeats());
            ride.setStatus("ACTIVE");
            ride.setCreatedAt(LocalDateTime.now());
            ride.setUpdatedAt(LocalDateTime.now());

            Ride savedRide = rideService.getRideRepository().save(ride);
            responseDTO = rideService.convertToDTO(savedRide);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/rides
     * Get all rides
     */
    @GetMapping
    public ResponseEntity<List<RideResponseDTO>> getAllRides() {
        List<RideResponseDTO> rides = rideService.getAllRides();
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    /**
     * GET /api/v1/rides/{id}
     * Get ride by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDTO> getRideById(@PathVariable Long id) {
        RideResponseDTO ride = rideService.getRideById(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    /**
     * GET /api/v1/rides/driver/{driverId}
     * Get all rides by driver
     */
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RideResponseDTO>> getRidesByDriver(@PathVariable Long driverId) {
        List<RideResponseDTO> rides = rideService.getRidesByDriver(driverId);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    /**
     * GET /api/v1/rides/search
     * Search available rides by source and destination
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchRides(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam(defaultValue = "false", required = false) boolean premium,
            @RequestHeader("X-User-Id") Long passengerId) {

        System.out.println("\nPassenger #" + passengerId + " searching: " + source + " → " + destination
                + (premium ? " [PREMIUM MODE]" : ""));

        List<?> rides = rideService.searchAvailableRidesWithPremium(source, destination, premium);

        if (premium) {
            System.out.println("Returning " + rides.size() + " premium rides");
        } else {
            System.out.println("✓ Returning " + rides.size() + " regular rides");
        }

        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    /**
     * GET /api/v1/rides/upcoming
     * Get upcoming rides
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<RideResponseDTO>> getUpcomingRides() {
        List<RideResponseDTO> rides = rideService.getUpcomingRides();
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    /**
     * PUT /api/v1/rides/{id}
     * Update an existing ride
     */
    @PutMapping("/{id}")
    public ResponseEntity<RideResponseDTO> updateRide(
            @PathVariable Long id,
            @RequestBody RideRequestDTO requestDTO,
            @RequestHeader("X-User-Id") Long hostDriverId) {
        RideResponseDTO responseDTO = rideService.updateRide(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * DELETE /api/v1/rides/{id}
     * Delete a ride
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRide(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long hostDriverId) {
        rideService.deleteRide(id);
        return new ResponseEntity<>("Ride deleted successfully", HttpStatus.OK);
    }

    /**
     * POST /api/v1/rides/{id}/cancel
     * Cancel a ride
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<RideResponseDTO> cancelRide(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long hostDriverId) {
        RideResponseDTO responseDTO = rideService.cancelRide(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // ============ NEW ROUTES - COMMAND PATTERN ============

    /**
     * PUT /api/v1/rides/{id}/command
     * Update ride with Command Pattern (has undo capability)
     */
    @PutMapping("/{id}/command")
    public ResponseEntity<RideResponseDTO> updateRideWithCommand(
            @PathVariable Long id,
            @RequestBody RideRequestDTO requestDTO,
            @RequestHeader("X-User-Id") Long hostDriverId) {

        RideResponseDTO responseDTO = rideService.updateRideWithCommand(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * POST /api/v1/rides/{id}/cancel/command
     * Cancel ride with Command Pattern (has undo capability)
     */
    @PostMapping("/{id}/cancel/command")
    public ResponseEntity<RideResponseDTO> cancelRideWithCommand(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long hostDriverId) {

        RideResponseDTO responseDTO = rideService.cancelRideWithCommand(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * POST /api/v1/rides/undo
     * Undo last operation
     */
    @PostMapping("/undo")
    public ResponseEntity<String> undoLastOperation(
            @RequestHeader("X-User-Id") Long hostDriverId) {

        rideService.undoLastOperation();
        return new ResponseEntity<>("Last operation undone successfully", HttpStatus.OK);
    }
}