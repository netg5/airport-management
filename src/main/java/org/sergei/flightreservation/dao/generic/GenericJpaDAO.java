/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericJpaDAO<T extends Serializable> {
    void setPersistentClass(Class<T> persistentClass);

    T findOne(final Long aLong);

    List<T> findAll();

    void save(final T entity);

    void update(final T entity);

    void delete(final T entity);
}
