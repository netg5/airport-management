/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class UserDAO extends AbstractJpaDAO<User> {

    public UserDAO() {
        setPersistentClass(User.class);
    }

    public User findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u  WHERE u.username = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
