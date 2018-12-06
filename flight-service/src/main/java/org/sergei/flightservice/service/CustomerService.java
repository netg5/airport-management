package org.sergei.flightservice.service;

import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Find customer by ID
     *
     * @param customerId get customer ID as a parameter
     * @return customer
     */
    public Customer findOne(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
    }

    /**
     * Find all customers
     *
     * @return list of customers
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Save customer
     *
     * @param customer gets customer DTO as a parameter
     * @return customer DTO as a response
     */
    public Customer save(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Method to update customer details
     *
     * @param customerId gets customer ID as a parameter
     * @param customer   gets customer DO as a prameter
     * @return customer DTO as a response
     */
    public Customer update(Long customerId, Customer customer) {
        customer.setCustomerId(customerId);

        Customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        foundCustomer.setFirstName(foundCustomer.getFirstName());
        foundCustomer.setLastName(foundCustomer.getLastName());
        foundCustomer.setAge(foundCustomer.getAge());

        customerRepository.save(foundCustomer);

        return foundCustomer;
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    public Customer delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        customerRepository.delete(customer);
        return customer;
    }
}
