/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class RouteDTO {
    private Long routeId;
    private Double distance;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private String place;
    private List<AircraftDTO> aircraftDTOList = new LinkedList<>();

    public RouteDTO() {
    }

    public RouteDTO(Long routeId, Double distance, LocalDateTime departureTime,
                    LocalDateTime arrivalTime, BigDecimal price, String place, List<AircraftDTO> aircraftDTOList) {
        this.routeId = routeId;
        this.distance = distance;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.place = place;
        this.aircraftDTOList = aircraftDTOList;
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

    public List<AircraftDTO> getAircraftDTOList() {
        return aircraftDTOList;
    }

    public void setAircraftDTOList(List<AircraftDTO> aircraftDTOList) {
        this.aircraftDTOList = aircraftDTOList;
    }
}
