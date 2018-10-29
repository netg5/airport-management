/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import java.util.List;

public interface IService<T> {
    T findOne(Long aLong);

    List<T> findAll();

    T save(T entity);

    T update(Long aLong, T entity);

    T delete(Long aLong);
}
