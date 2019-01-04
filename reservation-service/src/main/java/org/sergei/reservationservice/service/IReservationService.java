package org.sergei.reservationservice.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface IReservationService<E, T> {

    /**
     * Find one reservation for customer
     *
     * @param customerId    Customer ID
     * @param reservationId reservation ID
     * @return reservation entity
     */
    E findOneForCustomer(Long customerId, Long reservationId);

    /**
     * Find all reservations for customer
     *
     * @param customerId customer ID to find reservations
     * @return list of reservations
     */
    List<E> findAllForCustomer(Long customerId);

    /**
     * Find all reservations for customer paginated
     *
     * @param customerId Customer ID whoose reservations should be found
     * @param page       number of page to show
     * @param size       number of elements per page
     * @return collection of entities
     */
    Page<E> findAllForCustomerPaginated(Long customerId, int page, int size);

    /**
     * Save reservation for customer
     *
     * @param aLong     Customer ID who made reservation
     * @param entityDTO reservation body
     * @return saved reservation
     */
    T saveReservation(Long aLong, T entityDTO);

    /**
     * Update reservation details
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    T updateReservation(Long customerId, Long reservationId, Map<String, Object> params);

    /**
     * Method to delete reservation
     *
     * @param customerId    customer who made reservations
     * @param reservationId made reservation ID
     * @return deleted reservation entity
     */
    T deleteReservation(Long customerId, Long reservationId);
}
