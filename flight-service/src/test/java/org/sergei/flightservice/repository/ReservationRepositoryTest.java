package org.sergei.flightservice.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.model.Reservation;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Test for {@link ReservationRepository}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
@SuppressWarnings("all")
public class ReservationRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateTime DEPARTURE_TIME = LocalDateTime.parse("2018-09-09 09:24:00", FORMATTER);
    private static final LocalDateTime ARRIVAL_TIME = LocalDateTime.parse("2018-09-09 09:24:00", FORMATTER);
    private static final BigDecimal PRICE = new BigDecimal(25000);

    @Autowired
    @Qualifier("reservationRepository")
    private ReservationRepository reservationRepository;

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("aircraftRepository")
    private AircraftRepository aircraftRepository;

    @Autowired
    @Qualifier("routeRepository")
    private RouteRepository routeRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Reservation> reservationList = reservationRepository.findAll();
        assertTrue(reservationList.isEmpty());
    }

    @Test
    public void saveReservation_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Reservation reservation = new Reservation(DEPARTURE_TIME, customer, route);
        reservationRepository.save(reservation);
        Iterable<Reservation> foundAll = reservationRepository.findAll();
        assertThat(foundAll).hasSize(1);
        customer.setCustomerId(1L);
        assertThat(foundAll).contains(reservation);
    }

    @Test
    public void findOneForCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Aircraft aircraft = new Aircraft( "T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Reservation reservation = new Reservation(DEPARTURE_TIME, customer, route);
        reservationRepository.save(reservation);
        Optional<Reservation> foundReservation =
                reservationRepository.findOneForCustomer(
                        customer.getCustomerId(), reservation.getReservationId());
        assertEquals(reservation.getReservationId(), foundReservation.get().getReservationId());
        assertEquals(customer.getCustomerId(), foundReservation.get().getCustomer().getCustomerId());
        assertThat(foundReservation).contains(reservation);
    }

    @Test
    public void getAllForCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Reservation reservation = new Reservation( DEPARTURE_TIME, customer, route);
        reservationRepository.save(reservation);
        Optional<List<Reservation>> foundReservations =
                reservationRepository.findAllForCustomer(customer.getCustomerId());
        assertEquals(foundReservations.get().size(), 1);
    }
}
