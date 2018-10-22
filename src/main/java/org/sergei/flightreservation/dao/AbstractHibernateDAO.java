package org.sergei.flightreservation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDAO<T extends Serializable> implements IDAO<T> {

    private Class<T> persistentClass;

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public T findOne(Long aLong) {
        return getCurrentSession().get(persistentClass, aLong);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + persistentClass.getName()).list();
    }

    @Override
    public void save(T entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
