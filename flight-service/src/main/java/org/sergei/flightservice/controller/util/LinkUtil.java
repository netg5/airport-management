package org.sergei.flightservice.controller.util;

import org.sergei.flightservice.controller.AircraftController;
import org.sergei.flightservice.controller.CustomerController;
import org.sergei.flightservice.controller.ReservationController;
import org.sergei.flightservice.controller.RouteController;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.dto.RouteDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

/**
 * @author Sergei Visotsky
 * @since 12/16/2018
 */
public final class LinkUtil {

    /**
     * Method to set HATEOAS links for customer
     *
     * @param customerDTO get customer DTO to setup links
     * @return customer DTO with links;
     */
    public static CustomerDTO setLinksForCustomer(CustomerDTO customerDTO) {
        Link selfLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getCustomerId())).withSelfRel();
        Link reservationsLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ReservationController.class)
                        .getAllForCustomer(customerDTO.getCustomerId())).withRel("reservations");
        Link ticketsLink = new Link(
                "http://127.0.0.1:8080/ticket-api/tickets?customerId=" + customerDTO.getCustomerId()).withRel("tickets");
        Link link = ControllerLinkBuilder.linkTo(CustomerController.class).withRel("allCustomers");
        customerDTO.add(selfLink);
        customerDTO.add(reservationsLink);
        customerDTO.add(ticketsLink);
        customerDTO.add(link);
        return customerDTO;
    }

    /**
     * Method to set HATEOAS links for aircraft
     *
     * @param aircraftDTO get DTO to setup links
     * @return DTO with links
     */
    public static AircraftDTO setLinksForAircraft(AircraftDTO aircraftDTO) {
        Link selfLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(AircraftController.class).getAircraftById(aircraftDTO.getAircraftId())).withSelfRel();
        Link link = ControllerLinkBuilder.linkTo(AircraftController.class).withRel("allAircrafts");
        aircraftDTO.add(selfLink);
        aircraftDTO.add(link);
        return aircraftDTO;
    }

    /**
     * Method to set HATEOAS links for route
     *
     * @param routeDTO get route DTO to set links
     * @return DTO with links added
     */
    public static RouteDTO setLinksForRoute(RouteDTO routeDTO) {
        Link link = ControllerLinkBuilder.linkTo(RouteController.class).withRel("allRoutes");
        Link selfLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(RouteController.class).getRouteById(routeDTO.getRouteId())).withSelfRel();
        routeDTO.add(selfLink);
        routeDTO.add(link);
        return routeDTO;
    }
}
