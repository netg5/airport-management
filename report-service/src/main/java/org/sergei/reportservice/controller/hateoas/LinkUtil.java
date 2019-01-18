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

package org.sergei.reportservice.controller.hateoas;

import org.sergei.reportservice.controller.AircraftReportController;
import org.sergei.reportservice.controller.CustomerReportController;
import org.sergei.reportservice.dto.AircraftReportDTO;
import org.sergei.reportservice.dto.CustomerReportDTO;
import org.sergei.reportservice.model.Reservation;
import org.sergei.reportservice.properties.GatewayPortProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Class to set links for the REST responses
 *
 * @author Sergei Visotsky
 */
@Component
public class LinkUtil {

    private final GatewayPortProperties gatewayPortProperties;

    @Autowired
    public LinkUtil(GatewayPortProperties gatewayPortProperties) {
        this.gatewayPortProperties = gatewayPortProperties;
    }

    /**
     * Set links for each report in collection
     *
     * @param aircraftReports collection of reports
     * @return collection with links set
     */
    public Resources setLinksForAllReports(Page<AircraftReportDTO> aircraftReports) {
        aircraftReports.forEach(aircraftReportDTO -> {
            Link link = linkTo(
                    methodOn(AircraftReportController.class)
                            .findByAircraftId(aircraftReportDTO.getAircraftId())).withRel("aircraft");
            aircraftReportDTO.add(link);
            setLinksForEachReservation(aircraftReportDTO.getReservationList());
        });
        return setServletResourceLinks(aircraftReports);
    }

    /**
     * Set link for required report
     *
     * @param aircraftReportDTO Report DTO to set links
     * @return DTO with links set
     */
    public AircraftReportDTO setLinksForAircraftReport(AircraftReportDTO aircraftReportDTO) {
        List<Reservation> reservationList = aircraftReportDTO.getReservationList();
        setLinksForEachReservation(reservationList);
        Link selfLink = linkTo(
                methodOn(AircraftReportController.class)
                        .findByAircraftId(aircraftReportDTO.getAircraftId())).withRel("aircraftSelf");
        Link link = linkTo(AircraftReportController.class).withRel("allReports");
        aircraftReportDTO.add(selfLink);
        aircraftReportDTO.add(link);
        return aircraftReportDTO;
    }

    /**
     * Method to set links for customer report
     *
     * @param customerReportDTO customer report pojo
     * @return pojo with links set everywhere it needs
     */
    public CustomerReportDTO setLinksForCustomerReport(CustomerReportDTO customerReportDTO) {
        List<Reservation> reservationList = customerReportDTO.getReservations();
        setLinksForEachReservation(reservationList);
        Link selfLink = linkTo(
                methodOn(CustomerReportController.class)
                        .findReportByCustomerId(customerReportDTO.getCustomerId())).withSelfRel();
        customerReportDTO.add(selfLink);
        return customerReportDTO;
    }

    /**
     * Method to se links for each aircraft reservation
     *
     * @param reservationList collection of aircraft report payloads to set links for each
     */
    private void setLinksForEachReservation(List<Reservation> reservationList) {
        int index = 0;
        for (Reservation reservation : reservationList) {
            Link reservationLink = new Link(
                    "https://127.0.0.1:" + gatewayPortProperties.getPort() + "/reservation-api/customers/" +
                            reservationList.get(index).getReservationId()).withRel("reportSelf");
            reservation.add(reservationLink);
            index++;
        }
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
