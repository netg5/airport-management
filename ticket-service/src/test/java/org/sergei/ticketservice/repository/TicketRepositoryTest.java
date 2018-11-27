package org.sergei.ticketservice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest(properties = {"spring.cloud.config.enabled=false", "spring.cloud.config.discovery.enabled=false"})
@ContextConfiguration(classes = {WebSecurityConfig.class})
@EnableJpaRepositories(basePackages = "org.sergei.ticketservice.repository")
@EntityScan(basePackages = "org.sergei.ticketservice.model")
public class TicketRepositoryTest {

    @Autowired
    @Qualifier("ticketRepository")
    private TicketRepository ticketRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Ticket> aircraftList = ticketRepository.findAll();
        assertTrue(aircraftList.isEmpty());
    }
}
