package org.sergei.flightservice.service;

import org.sergei.flightservice.dto.ReservationDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 * @since 12/10/2018
 */
public interface IReservationService<E, T> extends IService {
    E findOneForCustomer(Long aLong, Long aLong2);

    List<E> findAllForCustomer(Long customerId);

    T saveReservation(Long aLong, T entityDTO);

    T updateReservation(Long aLong, Long aLong2, T entityDTO);

    T patchReservation(Long aLong, Map<String, Object> params);
}
