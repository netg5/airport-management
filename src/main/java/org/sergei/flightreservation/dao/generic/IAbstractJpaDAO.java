/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao.generic;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
public interface IAbstractJpaDAO<T extends Serializable> {
    void setPersistentClass(Class<T> persistentClass);

    T findOne(Long aLong);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
