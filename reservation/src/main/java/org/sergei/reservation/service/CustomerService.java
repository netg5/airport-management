package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.CustomerResponseDTO;
import org.sergei.reservation.rest.dto.CustomerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface CustomerService {
    ResponseEntity<ResponseDTO<CustomerResponseDTO>> findOne(Long customerId);

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> findAll();

    ResponseEntity<ResponseDTO<String>> findIdsOfAllCustomers();

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> findAllPaginated(int page, int size);

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> save(CustomerResponseDTO customerResponseDTO);

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> update(CustomerUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> patch(Long customerId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<CustomerResponseDTO>> delete(Long customerId);
}
