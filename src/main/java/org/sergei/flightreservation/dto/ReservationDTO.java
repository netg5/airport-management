/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dto;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
public class ReservationDTO {
    private Long reservationId;
    private Long customerId;
    private Long routeId;
    private LocalDateTime reservationDate;
    private CustomerDTO customerDTO;
    private RouteDTO routeDTO;
//    private List<Route> routeList = new LinkedList<>();

    public ReservationDTO() {
    }

    public ReservationDTO(Long reservationId, Long customerId, Long routeId,
                          LocalDateTime reservationDate, RouteDTO routeDTO, CustomerDTO customerDTO) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
//        this.routeList = routeList;
        this.routeId = routeId;
        this.customerDTO = customerDTO;
        this.routeDTO = routeDTO;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public RouteDTO getRouteDTO() {
        return routeDTO;
    }

    public void setRouteDTO(RouteDTO routeDTO) {
        this.routeDTO = routeDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
