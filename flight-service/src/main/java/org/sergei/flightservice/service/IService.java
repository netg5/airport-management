package org.sergei.flightservice.service;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 * @since 12/10/2018
 */
public interface IService<E> {
    E findOne(Long aLong);

    List<E> findAll();

    E save(E entityDTO);

    E update(Long aLong, E entityDTO);

    E patch(Long aLong, Map<String, Object> params);

    E delete(Long aLong);
}
