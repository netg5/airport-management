package org.sergei.flightservice.persistence;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.test.config.ApplicationJpaConfig;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, WebSecurityConfigurerAdapter.class})
@ContextConfiguration(classes = {ApplicationJpaConfig.class, WebSecurityConfig.class})
public class InMemoryDbTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        testEntityManager.persist(customer);
        testEntityManager.flush();

//        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundCustomer).getFirstName(), customer.getFirstName());
    }
}
