package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.dto.RouteDTO;
import org.sergei.flightservice.dto.RouteExtendedDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class RouteService {

    private final ModelMapper modelMapper;
    private final AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;

    @Autowired
    public RouteService(ModelMapper modelMapper,
                        AircraftRepository aircraftRepository, RouteRepository routeRepository) {
        this.modelMapper = modelMapper;
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    public RouteExtendedDTO findOne(Long routeId) {
        // Find route and map into the extended DTO
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Route with this ID not found")
                );
        RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

        // Find aircraft map it into the aircraft DTO
        Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aircraft with this ID not found")
                );
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
        List<Route> routeList = routeRepository.findAll();
        List<RouteExtendedDTO> routeExtendedDTOList = ObjectMapperUtils.mapAll(routeList, RouteExtendedDTO.class);

        int counter = 0;
        for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
            Aircraft aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Aircraft with this ID not found")
                    );
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
        Aircraft aircraft = aircraftRepository.findById(routeDTO.getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aircraft with this ID not found")
                );
        route.setAircraft(aircraft);
        routeRepository.save(route);
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

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Route with this ID not found")
                );
        route.setDistance(routeDTO.getDistance());
        route.setDepartureTime(routeDTO.getDepartureTime());
        route.setArrivalTime(routeDTO.getArrivalTime());
        route.setPrice(routeDTO.getPrice());
        route.setPrice(routeDTO.getPrice());
        route.setPlace(routeDTO.getPlace());
        route.setAircraft(
                aircraftRepository.findById(routeDTO.getAircraftId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Aircraft with this ID not found")
                        )
        );
        routeRepository.save(route);

        return routeDTO;
    }

    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    public RouteDTO delete(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Route with this ID not found")
                );
        routeRepository.delete(route);
        return modelMapper.map(route, RouteDTO.class);
    }
}
