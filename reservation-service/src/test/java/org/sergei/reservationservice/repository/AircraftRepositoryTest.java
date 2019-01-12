/*
 * Copyright 2018-2019 Sergei Visotsky
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

package org.sergei.reservationservice.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test for {@link AircraftRepository}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservationservice.repository")
@EntityScan(basePackages = "org.sergei.reservationservice.model")
public class AircraftRepositoryTest {

    @Autowired
    @Qualifier("aircraftRepository")
    private AircraftRepository aircraftRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        assertTrue(aircraftList.isEmpty());
    }

    @Test
    public void saveAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Iterable<Aircraft> foundAll = aircraftRepository.findAll();
        assertThat(foundAll).hasSize(1);
        aircraft.setAircraftId(1L);
        assertThat(foundAll).contains(aircraft);
    }

    @Test
    public void saveAircraft_deleteAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Iterable<Aircraft> foundAll = aircraftRepository.findAll();
        Assertions.assertThat(foundAll).hasSize(1);
        aircraft.setAircraftId(1L);
        Assertions.assertThat(foundAll).contains(aircraft);
        aircraftRepository.delete(aircraft);
        Iterable<Aircraft> aircraftList = aircraftRepository.findAll();
        Assertions.assertThat(aircraftList).hasSize(0);
    }
}
