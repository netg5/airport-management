package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.BaseCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerRepository extends JpaRepository<BaseCustomer, Long> {
}
