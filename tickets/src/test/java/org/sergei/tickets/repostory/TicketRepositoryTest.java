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

package org.sergei.tickets.repostory;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.jpa.repository.TicketRepository;
import org.sergei.tickets.testconfig.WebSecurityConfigTest;
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
@EnableJpaRepositories(basePackages = "org.sergei.tickets.jpa.repository")
@EntityScan(basePackages = "org.sergei.tickets.jpa.model")
/*@Sql(scripts = {
        "classpath:/sql/db-test-schema.sql",
        "classpath:/sql/db-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)*/
public class TicketRepositoryTest {

    @Autowired
    @Qualifier("ticketRepository")
    private TicketRepository ticketRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Ticket> ticketList = ticketRepository.findAll();
        assertTrue(ticketList.isEmpty());
    }

    @Test
    public void getTicketsForCustomer() {
        List<Ticket> ticketList = ticketRepository.findAllTickets(1L, "Riga", 2500.0);
        assertEquals(1, ticketList.size());
    }
}