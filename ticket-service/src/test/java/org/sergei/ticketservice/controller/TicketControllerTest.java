package org.sergei.ticketservice.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.ticketservice.TicketServiceApplication;
import org.sergei.ticketservice.testconfig.AppConfigTest;
import org.sergei.ticketservice.testconfig.ResourceServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test for {@link TicketController}
 *
 * @author Sergei Visotsky
 * @since 12/13/2018
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppConfigTest.class, ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.ticketservice.repository")
@EntityScan(basePackages = "org.sergei.ticketservice.model")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testMethod() {

    }
}
