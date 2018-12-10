package org.sergei.flightservice.service;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/10/2018
 */
public interface IReservationService<E, T> extends IService {
    E findOneForCustomer(Long aLong, Long aLong2);

    List<E> findAllForCustomer(Long customerId);

    T saveReservation(Long aLong, T entityDTO);

    T updateReservation(Long aLong, Long aLong2, T entityDTO);
}
