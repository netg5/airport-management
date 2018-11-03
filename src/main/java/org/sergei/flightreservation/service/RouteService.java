/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dao.generic.GenericJpaDAO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.dto.RouteExtendedDTO;
import org.sergei.flightreservation.model.Aircraft;
import org.sergei.flightreservation.model.Route;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class RouteService {

    private final ModelMapper modelMapper;
    private final AircraftDAO aircraftDAO;

    private GenericJpaDAO<Route> genericDAO;

    @Autowired
    public RouteService(ModelMapper modelMapper, AircraftDAO aircraftDAO) {
        this.modelMapper = modelMapper;
        this.aircraftDAO = aircraftDAO;
    }

    @Autowired
    public void setGenericDAO(GenericJpaDAO<Route> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(Route.class);
    }

    public RouteDTO findOne(Long routeId) {
        Route route = genericDAO.findOne(routeId);
        return modelMapper.map(route, RouteDTO.class);
    }

    public List<RouteExtendedDTO> findAll() {
        List<Route> routeList = genericDAO.findAll();


        return ObjectMapperUtils.mapAll(routeList, RouteExtendedDTO.class);
    }

    public RouteDTO save(RouteDTO routeDTO) {
        Route route = modelMapper.map(routeDTO, Route.class);
        Aircraft aircraft = aircraftDAO.findOne(routeDTO.getAircraftId());
        route.setAircraft(aircraft);
        genericDAO.save(route);
        return routeDTO;
    }

    public RouteDTO update(Long routeId, RouteDTO routeDTO) {
        routeDTO.setRouteId(routeId);

        Route route = modelMapper.map(routeDTO, Route.class);
        genericDAO.save(route);

        return routeDTO;
    }

    public RouteDTO delete(Long routeId) {
        Route route = genericDAO.findOne(routeId);
        genericDAO.delete(route);
        return modelMapper.map(route, RouteDTO.class);
    }
}
