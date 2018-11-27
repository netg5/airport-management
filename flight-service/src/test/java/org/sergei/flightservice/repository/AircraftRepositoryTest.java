package org.sergei.flightservice.repository;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest(properties =
        {
                "spring.cloud.config.enabled=false",
                "spring.cloud.config.discovery.enabled=false"
        }
)
@ContextConfiguration(classes = {WebSecurityConfig.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
@ActiveProfiles("test")
public class AircraftRepositoryTest {

    @Autowired
    @Qualifier("aircraftRepository")
    private AircraftRepository aircraftRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        Assert.assertTrue(aircraftList.isEmpty());
    }

    @Test
    public void saveAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraft.setAircraftId(1L);
        aircraftRepository.save(aircraft);
        Aircraft foundAircraft = aircraftRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundAircraft).getAircraftName(), aircraft.getAircraftName());
    }

    @Test
    public void saveAircraft_deleteAircraft_thenGetOk() {
        Aircraft aircraft = new Aircraft("T_50", "TestName", 2000.0, 3000);
        aircraftRepository.save(aircraft);
        Aircraft foundAircraft = aircraftRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundAircraft).getAircraftName(), aircraft.getAircraftName());
        aircraftRepository.delete(foundAircraft);
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        Assert.assertTrue(aircraftList.isEmpty());
    }
}
