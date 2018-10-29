/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dto;

public class AircraftDTO {
    private Long aircraftId;
    private String model;
    private String aircraftName;
    private Double aircraftWeight;
    private Integer maxPassengers;

    public AircraftDTO() {
    }

    public AircraftDTO(Long aircraftId, String model, String aircraftName, Double aircraftWeight, Integer maxPassengers) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.aircraftName = aircraftName;
        this.aircraftWeight = aircraftWeight;
        this.maxPassengers = maxPassengers;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAircraftName() {
        return aircraftName;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public Double getAircraftWeight() {
        return aircraftWeight;
    }

    public void setAircraftWeight(Double aircraftWeight) {
        this.aircraftWeight = aircraftWeight;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }
}
