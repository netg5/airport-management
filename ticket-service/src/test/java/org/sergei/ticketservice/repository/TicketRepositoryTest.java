package org.sergei.ticketservice.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test for {@link TicketRepository}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.ticketservice.repository")
@EntityScan(basePackages = "org.sergei.ticketservice.model")
//@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TicketRepositoryTest {

    @Autowired
    @Qualifier("ticketRepository")
    private TicketRepository ticketRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Ticket> ticketList = ticketRepository.findAll();
        assertTrue(ticketList.isEmpty());
    }

    @Ignore
    @Test
    public void getTicketsForCustomer() {
        List<Ticket> ticketList = ticketRepository.findByCustomerIdPlaceOrDistance(1L, "Riga", 2500.0);
        assertEquals(1, ticketList.size());
    }
}
