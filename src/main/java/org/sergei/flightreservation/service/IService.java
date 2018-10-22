package org.sergei.flightreservation.service;

import java.io.Serializable;
import java.util.List;

public interface IService<T> {
    T findOne(Long aLong);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
