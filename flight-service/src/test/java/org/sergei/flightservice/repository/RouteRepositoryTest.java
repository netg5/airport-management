package org.sergei.flightservice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.test.config.WebSecurityConfigTest;
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

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link RouteRepository}
 *
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class RouteRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateTime DEPARTURE_TIME = LocalDateTime.parse("2018-09-09 09:24:00", FORMATTER);
    private static final LocalDateTime ARRIVAL_TIME = LocalDateTime.parse("2018-09-09 09:24:00", FORMATTER);
    private static final BigDecimal PRICE = new BigDecimal(25000);

    @Autowired
    @Qualifier("routeRepository")
    private RouteRepository routeRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Route> routeList = routeRepository.findAll();
        assertTrue(routeList.isEmpty());
    }

    @Test
    public void saveRoute_thenGetOk() {
        Aircraft aircraft = new Aircraft(1L, "T_50", "TestName", 2000.0, 3000);
        Route route = new Route(1L, 250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Iterable<Route> foundAll = routeRepository.findAll();
        assertThat(foundAll).hasSize(1);
        route.setRouteId(1L);
        assertThat(foundAll).contains(route);
    }

    @Test
    public void saveRoute_deleteRoute_thenGetOk() {
        Aircraft aircraft = new Aircraft(1L, "T_50", "TestName", 2000.0, 3000);
        Route route = new Route(1L, 250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, null);
        routeRepository.save(route);
        Iterable<Route> foundAll = routeRepository.findAll();
        assertThat(foundAll).hasSize(1);
        route.setRouteId(1L);
        assertThat(foundAll).contains(route);
        routeRepository.delete(route);
        Iterable<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(0);
    }
}
