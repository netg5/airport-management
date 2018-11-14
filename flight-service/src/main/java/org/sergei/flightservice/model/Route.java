/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Entity
@Table(name = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_seq")
    @SequenceGenerator(name = "route_seq", sequenceName = "route_seq", allocationSize = 1)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "place", nullable = false)
    private String place;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "reservation_id")
    private List<FlightReservation> flightReservationList = new LinkedList<>();

    public Route() {
    }

    public Route(Double distance, LocalDateTime departureTime,
                 LocalDateTime arrivalTime, BigDecimal price, String place,
                 Aircraft aircraft, List<FlightReservation> flightReservationList) {
        this.distance = distance;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.place = place;
        this.aircraft = aircraft;
        this.flightReservationList = flightReservationList;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public List<FlightReservation> getFlightReservationList() {
        return flightReservationList;
    }

    public void setFlightReservationList(List<FlightReservation> flightReservationList) {
        this.flightReservationList = flightReservationList;
    }
}
