package org.sergei.ticketservice.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.ticketservice.testconfig.WebSecurityConfigTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link TicketController}
 *
 * @author Sergei Visotsky
 * @since 12/13/2018
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.ticketservice.repository")
@EntityScan(basePackages = "org.sergei.ticketservice.model")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
public class TicketControllerTest {

    @Test
    public void testMethod() {

    }
}
