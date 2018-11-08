/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.Customer;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class CustomerDAO extends AbstractJpaDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}
