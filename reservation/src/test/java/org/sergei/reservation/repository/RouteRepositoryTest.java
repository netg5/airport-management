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

package org.sergei.reservation.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.jpa.repository.RouteRepository;
import org.sergei.reservation.testconfig.WebSecurityConfigTest;
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
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservation.jpa.repository")
@EntityScan(basePackages = "org.sergei.reservation.jpa.model")
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
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, Collections.emptyList());
        routeRepository.save(route);
        Iterable<Route> allFound = routeRepository.findAll();
        assertThat(allFound).hasSize(1);
//        route.setRouteId(1L);
        assertThat(allFound).contains(route);
    }

    @Test
    public void saveRoute_deleteRoute_thenGetOk() {
        Aircraft aircraft = new Aircraft( "T_50", "TestName", 2000.0, 3000);
        Route route = new Route(250.03, DEPARTURE_TIME, ARRIVAL_TIME, PRICE, "Riga", aircraft, null);
        routeRepository.save(route);
        Iterable<Route> allFound = routeRepository.findAll();
        assertThat(allFound).hasSize(1);
//        route.setRouteId(1L);
        assertThat(allFound).contains(route);
        routeRepository.delete(route);
        Iterable<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(0);
    }
}
