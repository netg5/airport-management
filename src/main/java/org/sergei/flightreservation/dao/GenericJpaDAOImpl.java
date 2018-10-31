/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDAOImpl<T extends Serializable> extends AbstractJpaDAO<T> implements GenericJpaDAO<T> {
}
