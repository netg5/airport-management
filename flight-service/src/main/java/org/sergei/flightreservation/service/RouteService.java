/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dao.RouteDAO;
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
    private RouteDAO routeDAO;

    @Autowired
    public RouteService(ModelMapper modelMapper, AircraftDAO aircraftDAO, RouteDAO routeDAO) {
        this.modelMapper = modelMapper;
        this.aircraftDAO = aircraftDAO;
        this.routeDAO = routeDAO;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    public RouteExtendedDTO findOne(Long routeId) {
        // Find route and map into the extended DTO
        Route route = routeDAO.findOne(routeId);
        if (route == null) {
            throw new ResourceNotFoundException("Route with this ID not found");
        }
        RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

        // Find aircraft map it into the aircraft DTO
        Aircraft aircraft = aircraftDAO.findOne(route.getAircraft().getAircraftId());
        AircraftDTO aircraftDTO = modelMapper.map(aircraft, AircraftDTO.class);

        // Set to the route extended DTO
        routeExtendedDTO.setAircraftDTO(aircraftDTO);
        return routeExtendedDTO;
    }

    /**
     * Find all routes
     *
     * @return list of route extended DTO
     */
    public List<RouteExtendedDTO> findAll() {
        List<Route> routeList = routeDAO.findAll();
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

    /**
     * Save route
     *
     * @param routeDTO gets route DTO as an input argument
     * @return route DTO as a response
     */
    public RouteDTO save(RouteDTO routeDTO) {
        Route route = modelMapper.map(routeDTO, Route.class);

        // Find aircraft required in request body
        Aircraft aircraft = aircraftDAO.findOne(routeDTO.getAircraftId());
        if (aircraft == null) {
            throw new ResourceNotFoundException("Aorcraft with this ID not found");
        }
        route.setAircraft(aircraft);
        routeDAO.save(route);
        return routeDTO;
    }

    /**
     * Update route data
     *
     * @param routeId  get route ID as an input argument
     * @param routeDTO Route DTO with updated data
     * @return Route DTO as a response
     */
    public RouteDTO update(Long routeId, RouteDTO routeDTO) {
        routeDTO.setRouteId(routeId);

        Route route = modelMapper.map(routeDTO, Route.class);
        routeDAO.update(route);

        return routeDTO;
    }

    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    public RouteDTO delete(Long routeId) {
        Route route = routeDAO.findOne(routeId);
        routeDAO.delete(route);
        return modelMapper.map(route, RouteDTO.class);
    }
}
