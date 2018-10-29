/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
    void setPersistentClass(Class<T> persistentClass);

    T findOne(final Long aLong);

    List<T> findAll();

    void save(final T entity);

    void update(final T entity);

    void delete(final T entity);
}
