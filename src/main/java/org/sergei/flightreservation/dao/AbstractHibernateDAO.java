package org.sergei.flightreservation.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public abstract class AbstractHibernateDAO<T extends Serializable> {

    private Class<T> tClass;

    @Autowired
    private SessionFactory sessionFactory;

    public T findOne(Long id) {
        return null;
    }

    public List<T> findAll() {
        return null;
    }

    public T save(T entity) {
        return null;
    }

    public T update(T entity) {
        return null;
    }

    public T delete(T entity) {
        return null;
    }
}
