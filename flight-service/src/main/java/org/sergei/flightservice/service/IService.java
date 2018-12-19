package org.sergei.flightservice.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface IService<E> {
    E findOne(Long aLong);

    List<E> findAll();

    Page<E> findAllPaginated(int page, int size);

    E save(E entityDTO);

    E update(Long aLong, E entityDTO);

    E patch(Long aLong, Map<String, Object> params);

    E delete(Long aLong);
}
