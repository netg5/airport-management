package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends AbstractHibernateDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}
