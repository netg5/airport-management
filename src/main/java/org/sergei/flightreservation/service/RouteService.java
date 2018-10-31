/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.GenericJpaDAO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.model.Route;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class RouteService implements IService<RouteDTO> {

    @Autowired
    private ModelMapper modelMapper;
    private GenericJpaDAO<Route> genericDAO;

    @Autowired
    public void setGenericDAO(GenericJpaDAO<Route> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(Route.class);
    }

    @Override
    public RouteDTO findOne(Long routeId) {
        Route route = genericDAO.findOne(routeId);
        return modelMapper.map(route, RouteDTO.class);
    }

    @Override
    public List<RouteDTO> findAll() {
        List<Route> routeList = genericDAO.findAll();
        return ObjectMapperUtils.mapAll(routeList, RouteDTO.class);
    }

    @Override
    public RouteDTO save(RouteDTO routeDTO) {
        Route route = modelMapper.map(routeDTO, Route.class);
        genericDAO.save(route);
        return routeDTO;
    }

    @Override
    public RouteDTO update(Long routeId, RouteDTO routeDTO) {
        routeDTO.setRouteId(routeId);

        Route route = modelMapper.map(routeDTO, Route.class);
        genericDAO.save(route);

        return routeDTO;
    }

    @Override
    public RouteDTO delete(Long routeId) {
        Route route = genericDAO.findOne(routeId);
        genericDAO.delete(route);
        return modelMapper.map(route, RouteDTO.class);
    }
}
