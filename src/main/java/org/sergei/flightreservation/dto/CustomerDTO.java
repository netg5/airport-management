/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dto;

import org.sergei.flightreservation.model.FlightReservation;

import java.util.LinkedList;
import java.util.List;

public class CustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;

    private List<FlightReservation> flightReservationList = new LinkedList<>();

    public CustomerDTO() {
    }

    public CustomerDTO(Long customerId, String firstName, String lastName, Integer age, List<FlightReservation> flightReservationList) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.flightReservationList = flightReservationList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<FlightReservation> getFlightReservationList() {
        return flightReservationList;
    }

    public void setFlightReservationList(List<FlightReservation> flightReservationList) {
        this.flightReservationList = flightReservationList;
    }
}
