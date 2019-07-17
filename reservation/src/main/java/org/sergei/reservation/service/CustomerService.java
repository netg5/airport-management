package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface CustomerService {
    ResponseEntity<CustomerDTO> findOne(Long customerId);

    ResponseEntity<List<CustomerDTO>> findAll();

    ResponseEntity<List<String>> findIdsOfAllCustomers();

    ResponseEntity<List<CustomerDTO>> findAllPaginated(int page, int size);

    ResponseEntity<CustomerDTO> save(CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> update(Long customerId, CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> patch(Long customerId, Map<String, Object> params);

    ResponseEntity<CustomerDTO> delete(Long customerId);
}
