package com.rideshare.ride.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RideResponseDTO {
    private Long id;
    private String source;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private BigDecimal price;
    private Integer totalSeats;
    private Integer availableSeats;
    private Long hostDriverId;
    private String status;
    private LocalDateTime createdAt;

    public RideResponseDTO() {}

    public RideResponseDTO(Long id, String source, String destination,
                           LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                           BigDecimal price, Integer totalSeats, Integer availableSeats,
                           Long hostDriverId, String status, LocalDateTime createdAt) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.hostDriverId = hostDriverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureDateTime() { return departureDateTime; }
    public void setDepartureDateTime(LocalDateTime departureDateTime) { this.departureDateTime = departureDateTime; }

    public LocalDateTime getArrivalDateTime() { return arrivalDateTime; }
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) { this.arrivalDateTime = arrivalDateTime; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public Long getHostDriverId() { return hostDriverId; }
    public void setHostDriverId(Long hostDriverId) { this.hostDriverId = hostDriverId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}