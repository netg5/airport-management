package org.sergei.flightservice.repository;

import org.sergei.flightservice.dto.CustomerIdsDTO;
import org.sergei.flightservice.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find ID of each customer in one JSON response as a list
     *
     * @return list of IDs
     */
    @Query("SELECT NEW org.sergei.flightservice.dto.CustomerIdsDTO(c.customerId) FROM Customer c")
    List<CustomerIdsDTO> findIdsOfAllCustomers();

    /**
     * Find ID of each customer in one JSON response as a list paginated
     *
     * @param pageable number of the page and number of the elements per page
     * @return Collection of customer IDs
     */
    @Query(value = "SELECT NEW org.sergei.flightservice.dto.CustomerIdsDTO(c.customerId) FROM Customer c",
            countQuery = "SELECT count(c) FROM Customer c")
    Page<CustomerIdsDTO> findIdsOfAllCustomersPaginated(Pageable pageable);
}
