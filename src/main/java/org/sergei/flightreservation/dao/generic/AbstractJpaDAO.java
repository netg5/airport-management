/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao.generic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractJpaDAO<T extends Serializable> implements IAbstractJpaDAO<T> {

    private Class<T> persistentClass;

    @Override
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T findOne(Long aLong) {
        return entityManager.find(persistentClass, aLong);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + persistentClass.getName()).getResultList();
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
