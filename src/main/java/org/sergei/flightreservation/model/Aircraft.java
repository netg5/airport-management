package org.sergei.flightreservation.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_seq", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "model")
    private String model;

    @Column(name = "aircraft_name")
    private String aircraftName;

    @Column(name = "weight")
    private Double aircraftWeight;

    @Column(name = "max_passengers")
    private Integer maxPassengers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "route_id")
    private Route route;

    public Aircraft() {
    }

    public Aircraft(String model, String aircraftName, Double aircraftWeight, Integer maxPassengers, Route route) {
        this.model = model;
        this.aircraftName = aircraftName;
        this.aircraftWeight = aircraftWeight;
        this.maxPassengers = maxPassengers;
        this.route = route;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
