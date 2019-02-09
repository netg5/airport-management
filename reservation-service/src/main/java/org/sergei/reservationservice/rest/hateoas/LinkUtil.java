/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservationservice.rest.hateoas;

import org.sergei.reservationservice.rest.AircraftController;
import org.sergei.reservationservice.rest.CustomerController;
import org.sergei.reservationservice.rest.ReservationController;
import org.sergei.reservationservice.rest.RouteController;
import org.sergei.reservationservice.rest.dto.*;
import org.sergei.reservationservice.config.properties.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Sergei Visotsky
 */
@Component
public class LinkUtil {

    private final GatewayProperties gatewayProperties;

    @Autowired
    public LinkUtil(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    /**
     * Set HATEOAS links for each customer in collection
     *
     * @param customerList get collection with customers
     * @return resource with customer
     */
    public Resources setLinksForAllCustomers(Iterable<CustomerDTO> customerList) {
        customerList.forEach(customer -> {
            Link link = linkTo(
                    methodOn(CustomerController.class)
                            .getCustomerById(customer.getCustomerId())).withSelfRel();
            Link reservationsLink = linkTo(
                    methodOn(ReservationController.class)
                            .getAllForCustomer(customer.getCustomerId())).withRel("reservations");
            Link ticketsLink = new Link("https://127.0.0.1:" + gatewayProperties.getPort() +
                    "/ticket-api/tickets?customerId=" + customer.getCustomerId()).withRel("tickets");
            customer.add(link);
            customer.add(reservationsLink);
            customer.add(ticketsLink);
        });
        return setServletResourceLinks(customerList);
    }

    public Resources setLinksForIdsOfCustomers(Iterable<CustomerIdsDTO> customerIdsDTOList) {
        Resources resources = setServletResourceLinks(customerIdsDTOList);
        Link allCustomers = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("allCustomers");
        resources.add(allCustomers);
        return resources;
    }

    /**
     * Method to set HATEOAS links for the customer response
     *
     * @param customerDTO get customer DTO to setup links
     * @return customer DTO with links;
     */
    public CustomerDTO setLinksForCustomer(CustomerDTO customerDTO) {
        Link selfLink = linkTo(
                methodOn(CustomerController.class)
                        .getCustomerById(customerDTO.getCustomerId())).withSelfRel();
        Link reservationsLink = linkTo(
                methodOn(ReservationController.class)
                        .getAllForCustomer(customerDTO.getCustomerId())).withRel("reservations");
        Link ticketsLink = new Link(
                "http://127.0.0.1:" + gatewayProperties.getPort() + "/ticket-api/tickets?customerId=" +
                        customerDTO.getCustomerId()).withRel("tickets");
        Link link = linkTo(CustomerController.class).withRel("allCustomers");
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
    public Resources setLinksForAllReservations(Iterable<ReservationExtendedDTO> reservations) {
        reservations.forEach(reservation -> {
            Link link = linkTo(
                    methodOn(ReservationController.class)
                            .getOneForCustomer(reservation.getCustomerId(),
                                    reservation.getReservationId())).withRel("reservation");
            reservation.add(link);
        });
        return setServletResourceLinks(reservations);
    }

    /**
     * Set HATEOAS links for each aircraft in collection
     *
     * @param aircrafts get collection of aircrafts
     * @return collection of aircrafts with links set
     */
    public Resources setLinksForAllAircrafts(Iterable<AircraftDTO> aircrafts) {
        aircrafts.forEach(aircraft -> {
            Link link = linkTo(
                    methodOn(AircraftController.class)
                            .getAircraftById(aircraft.getAircraftId())).withSelfRel();
            aircraft.add(link);
        });
        return setServletResourceLinks(aircrafts);
    }

    /**
     * Method to set HATEOAS links for the aircraft
     *
     * @param aircraftDTO get DTO to setup links
     * @return DTO with links
     */
    public AircraftDTO setLinksForAircraft(AircraftDTO aircraftDTO) {
        Link selfLink = linkTo(
                methodOn(AircraftController.class)
                        .getAircraftById(aircraftDTO.getAircraftId())).withSelfRel();
        Link link = linkTo(AircraftController.class).withRel("allAircrafts");
        aircraftDTO.add(selfLink);
        aircraftDTO.add(link);
        return aircraftDTO;
    }

    /**
     * Set links for each route
     *
     * @param routes collection of routes to set links
     * @return collection with links set
     */
    public Resources setLinksForEachRoute(Iterable<RouteExtendedDTO> routes) {
        routes.forEach(route -> {
            Link link = linkTo(
                    methodOn(RouteController.class)
                            .getRouteById(route.getRouteId())).withSelfRel();
            route.add(link);
        });
        return setServletResourceLinks(routes);
    }

    /**
     * Method to set HATEOAS links for route
     *
     * @param routeDTO get route DTO to set links
     * @return DTO with links added
     */
    public RouteDTO setLinksForRoute(RouteDTO routeDTO) {
        Link link = linkTo(RouteController.class).withRel("allRoutes");
        Link selfLink = linkTo(
                methodOn(RouteController.class)
                        .getRouteById(routeDTO.getRouteId())).withSelfRel();
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
    private static <E> Resources setServletResourceLinks(Iterable<E> collection) {
        Resources<E> resources = new Resources<>(collection);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return resources;
    }
}
