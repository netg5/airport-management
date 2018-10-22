package org.sergei.flightreservation.dao;

import java.io.Serializable;
import java.util.List;

public interface IDAO<T extends Serializable> {
    T findOne(Long aLong);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
