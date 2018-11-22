package org.sergei.flightservice.repository;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {WebSecurityConfig.class})
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
        Assert.assertTrue(routeList.isEmpty());
    }

    @Test
    public void saveAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Route foundRoute = routeRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundRoute).getPrice(), route.getPrice());
    }

    @Test
    public void saveAircraft_deleteAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Route foundRoute = routeRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundRoute).getPrice(), route.getPrice());
        routeRepository.delete(foundRoute);
        List<Route> routeList = routeRepository.findAll();
        Assert.assertTrue(routeList.isEmpty());
    }
}
