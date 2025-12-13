package com.rideshare.ride.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO for Ride
 * Contains all ride information to send to client
 */
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
    private LocalDateTime updatedAt;

    // NEW FIELDS FOR DECORATOR PATTERN
    private BigDecimal premiumPrice;
    private Integer bookableSeats;
    private String description;

    // ==================== Constructors ====================

    public RideResponseDTO() {
    }

    public RideResponseDTO(Long id, String source, String destination,
                           BigDecimal price, Integer totalSeats, Integer availableSeats,
                           Long hostDriverId, String status, LocalDateTime createdAt) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.hostDriverId = hostDriverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // ==================== Getters & Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Long getHostDriverId() {
        return hostDriverId;
    }

    public void setHostDriverId(Long hostDriverId) {
        this.hostDriverId = hostDriverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ==================== DECORATOR PATTERN GETTERS & SETTERS ====================

    /**
     * Premium price calculated by decorator (availableSeats × basePrice × 0.9)
     */
    public BigDecimal getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(BigDecimal premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    /**
     * Bookable seats determined by decorator (all available for premium)
     */
    public Integer getBookableSeats() {
        return bookableSeats;
    }

    public void setBookableSeats(Integer bookableSeats) {
        this.bookableSeats = bookableSeats;
    }

    /**
     * Description added by decorator pattern
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ==================== toString ====================

    @Override
    public String toString() {
        return "RideResponseDTO{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", arrivalDateTime=" + arrivalDateTime +
                ", price=" + price +
                ", totalSeats=" + totalSeats +
                ", availableSeats=" + availableSeats +
                ", hostDriverId=" + hostDriverId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", premiumPrice=" + premiumPrice +
                ", bookableSeats=" + bookableSeats +
                ", description='" + description + '\'' +
                '}';
    }
}