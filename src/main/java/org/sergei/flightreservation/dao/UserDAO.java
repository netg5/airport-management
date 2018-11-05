/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.User;
import org.sergei.flightreservation.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class UserDAO extends AbstractJpaDAO<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpService.class);

    public UserDAO() {
        setPersistentClass(User.class);
    }

    public User findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u  WHERE u.username = :username");
        query.setParameter("username", username);
        LOGGER.info(query.getSingleResult().toString());
        return (User) query.getSingleResult();
    }
}
