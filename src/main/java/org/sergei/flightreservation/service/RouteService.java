/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dao.generic.GenericJpaDAO;
import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.dto.RouteExtendedDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
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

    public RouteExtendedDTO findOne(Long routeId) {
        Route route = genericDAO.findOne(routeId);
        RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);
        Aircraft aircraft = aircraftDAO.findOne(route.getAircraft().getAircraftId());
        AircraftDTO aircraftDTO = modelMapper.map(aircraft, AircraftDTO.class);
        routeExtendedDTO.setAircraftDTO(aircraftDTO);
        return routeExtendedDTO;
    }

    public List<RouteExtendedDTO> findAll() {
        List<Route> routeList = genericDAO.findAll();
        List<RouteExtendedDTO> routeExtendedDTOList = ObjectMapperUtils.mapAll(routeList, RouteExtendedDTO.class);

        int counter = 0;
        for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
            Aircraft aircraft = aircraftDAO.findOne(routeList.get(counter).getAircraft().getAircraftId());
            AircraftDTO aircraftDTO = modelMapper.map(aircraft, AircraftDTO.class);
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            counter++;
        }

        return routeExtendedDTOList;
    }

    public RouteDTO save(RouteDTO routeDTO) {
        Route route = modelMapper.map(routeDTO, Route.class);
        Aircraft aircraft = aircraftDAO.findOne(routeDTO.getAircraftId());
        if (aircraft == null) {
            throw new ResourceNotFoundException("Aorcraft with this ID not found");
        }
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
