package org.sergei.flightservice.controller.util;

import org.sergei.flightservice.controller.AircraftController;
import org.sergei.flightservice.controller.CustomerController;
import org.sergei.flightservice.controller.ReservationController;
import org.sergei.flightservice.controller.RouteController;
import org.sergei.flightservice.dto.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Sergei Visotsky
 * @since 12/16/2018
 */
public final class LinkUtil<E> {

    /**
     * Set HATEOAS links for each customer of collection
     *
     * @param customerList get collection with customers
     * @return resource with customer
     */
    public static Resources setLinksForAllCustomers(Iterable<CustomerDTO> customerList) {
        customerList.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerController.class)
                            .getCustomerById(customer.getCustomerId())).withSelfRel();
            Link reservationsLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ReservationController.class)
                            .getAllForCustomer(customer.getCustomerId())).withRel("reservations");
            Link ticketsLink = new Link(
                    "http://127.0.0.1:8080/ticket-api/tickets?customerId=" + customer.getCustomerId()).withRel("tickets");
            customer.add(link);
            customer.add(reservationsLink);
            customer.add(ticketsLink);
        });
        return setServletResources(customerList);
    }

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
     * Set HATEOAS links for each reservation in collection
     *
     * @param reservations collection of reservations
     * @return collection of reservation with links set
     */
    public static Resources setLinksForAllReservations(Iterable<ReservationExtendedDTO> reservations) {
        reservations.forEach(reservation -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ReservationController.class)
                            .getOneForCustomer(reservation.getCustomerId(),
                                    reservation.getReservationId())).withRel("reservation");
            reservation.add(link);
        });
        return setServletResources(reservations);
    }


    /**
     * Set HATEOAS links for each aircraft of collection
     *
     * @param aircrafts get collection of aircrafts
     * @return collection of aircrafts with links set
     */
    public static Resources setLinksForAllAircrafts(Iterable<AircraftDTO> aircrafts) {
        aircrafts.forEach(aircraft -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(AircraftController.class)
                            .getAircraftById(aircraft.getAircraftId())).withSelfRel();
            aircraft.add(link);
        });
        return setServletResources(aircrafts);
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

    public static Resources setLinksForAllRoutes(Iterable<RouteExtendedDTO> routes) {
        routes.forEach(route -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(RouteController.class)
                            .getRouteById(route.getRouteId())).withSelfRel();
            route.add(link);
        });
        return setServletResources(routes);
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

    /**
     * Set HATEOAS links from servlet context
     *
     * @param collection collection of entities
     * @param <E>        Generic entity
     * @return resource with links set
     */
    private static <E> Resources setServletResources(Iterable<E> collection) {
        Resources<E> resources = new Resources<>(collection);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return resources;
    }
}
