package org.sergei.flightservice.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface IReservationService<E, T> extends IService {
    E findOneForCustomer(Long aLong, Long aLong2);

    List<E> findAllForCustomer(Long customerId);

    Page<E> findAllForCustomerPaginated(Long customerId, int page, int size);

    T saveReservation(Long aLong, T entityDTO);

    T updateReservation(Long aLong, Long aLong2, T entityDTO);

    T patchReservation(Long aLong, Map<String, Object> params);
}
