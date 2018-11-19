package org.sergei.flightservice.persistence;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.test.config.ApplicationJpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {ApplicationJpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDbTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);

        Customer secondCustomer = customerRepository.findById(1L).orElse(null);
        Assert.assertEquals("John", Objects.requireNonNull(secondCustomer).getFirstName());
    }
}
