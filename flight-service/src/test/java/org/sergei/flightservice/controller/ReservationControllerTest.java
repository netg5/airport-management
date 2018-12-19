package org.sergei.flightservice.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.model.Reservation;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.testconfig.ResourceServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link ReservationController}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class ReservationControllerTest {

    private static final String BASE_URL = "/customers";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllReservations_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00");
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00");
        final BigDecimal price = BigDecimal.valueOf(450.0);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final LocalDateTime reservationDate = LocalDateTime.parse("2018-09-28T22:00:00");
        setupReservation(reservationDate, customer, route);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservationId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].customerId").value(1))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservationDate").value(reservationDate))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0]._links.reservation.href").value("http://localhost/customers/2/reservations/1"))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.routeId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.distance").value(distance))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.departureTime").value(departureTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place").value(place))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.aircraft").value(place))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.model").value(model))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/customers/1/reservations"));
    }

    private Reservation setupReservation(LocalDateTime reservationDate, Customer customer, Route route) {
        Reservation reservation = new Reservation();

        reservation.setReservationDate(reservationDate);
        reservation.setCustomer(customer);
        reservation.setRoute(route);

        return reservation;
    }

    private Customer setupCustomer(String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);
        customer.setReservations(Collections.emptyList());

        return customerRepository.save(customer);
    }

    private Route setupRoute(Double distance, LocalDateTime departureTime,
                             LocalDateTime arrivalTime, BigDecimal price,
                             String place, Aircraft aircraft) {
        Route route = new Route();
        route.setDistance(distance);
        route.setDepartureTime(departureTime);
        route.setArrivalTime(arrivalTime);
        route.setPrice(price);
        route.setPlace(place);
        route.setAircraft(aircraft);
        return routeRepository.save(route);
    }

    private Aircraft setupAircraft(String model, String aircraftName,
                                   Double aircraftWeight, Integer maxPassengers) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(model);
        aircraft.setAircraftName(aircraftName);
        aircraft.setAircraftWeight(aircraftWeight);
        aircraft.setMaxPassengers(maxPassengers);
        return aircraftRepository.save(aircraft);
    }
}
