package org.sergei.flightservice.repository;

import org.sergei.flightservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
