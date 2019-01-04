package org.sergei.reservationservice.service;

import org.sergei.reservationservice.dto.AircraftDTO;
import org.sergei.reservationservice.dto.RouteDTO;
import org.sergei.reservationservice.dto.RouteExtendedDTO;
import org.sergei.reservationservice.exceptions.ResourceNotFoundException;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.model.Route;
import org.sergei.reservationservice.repository.AircraftRepository;
import org.sergei.reservationservice.repository.RouteRepository;
import org.sergei.reservationservice.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.sergei.reservationservice.util.ObjectMapperUtil.map;

/**
 * @author Sergei Visotsky
 */
@Service
public class RouteService implements IRouteService<RouteDTO, RouteExtendedDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

    private static final String ROUTE_NOT_FOUND = "Route with this ID not found";
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";

    private final AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;

    @Autowired
    public RouteService(AircraftRepository aircraftRepository, RouteRepository routeRepository) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    @Override
    public RouteExtendedDTO findOneRoute(Long routeId) {
        // Find route and map into the extended DTO
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );
        RouteExtendedDTO routeExtendedDTO = map(route, RouteExtendedDTO.class);

        // Find aircraftDTO map it into the aircraftDTO DTO
        Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);

        // Set to the route extended DTO
        routeExtendedDTO.setAircraftDTO(aircraftDTO);
        return routeExtendedDTO;
    }

    /**
     * Find all routes
     *
     * @return list of route extended DTO
     */
    @Override
    public List<RouteExtendedDTO> findAllRoutes() {
        List<Route> routeList = routeRepository.findAll();
        List<RouteExtendedDTO> routeExtendedDTOList = ObjectMapperUtil.mapAll(routeList, RouteExtendedDTO.class);

        int counter = 0;
        for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
            Aircraft aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            counter++;
        }

        return routeExtendedDTOList;
    }

    /**
     * Find paginated routes
     *
     * @param page number of pages shown
     * @param size number of elements on the page
     * @return list of entities
     */
    @Override
    public Page<RouteExtendedDTO> findAllRoutesPaginated(int page, int size) {
        Page<Route> routePage = routeRepository.findAll(PageRequest.of(page, size));
        Page<RouteExtendedDTO> routeExtendedDTOList = ObjectMapperUtil.mapAllPages(routePage, RouteExtendedDTO.class);
        int counter = 0;
        for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
            Aircraft aircraft = aircraftRepository.findById(routePage.getContent().get(counter).getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);
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
    @Override
    public RouteDTO save(RouteDTO routeDTO) {
        Route route = map(routeDTO, Route.class);

        // Find aircraftDTO required in request body
        Aircraft aircraft = aircraftRepository.findById(routeDTO.getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        LOGGER.debug("Found aircraft ID: {}", aircraft.getAircraftId());
        route.setAircraft(aircraft);
        Route savedRoute = routeRepository.save(route);
        LOGGER.debug("Aircraft ID in saved route: {}", savedRoute.getAircraft().getAircraftId());
        RouteDTO savedRouteDTO = map(savedRoute, RouteDTO.class);
        savedRouteDTO.setAircraftId(aircraft.getAircraftId());
        return savedRouteDTO;
    }

    /**
     * Update route data
     *
     * @param routeId  get route ID as an input argument
     * @param routeDTO Route DTO with updated data
     * @return Route DTO as a response
     */
    @Override
    public RouteDTO update(Long routeId, RouteDTO routeDTO) {
        routeDTO.setRouteId(routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
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
                                new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                        )
        );
        routeRepository.save(route);

        return routeDTO;
    }

    /**
     * Method to update one field of the route
     *
     * @param routeId ID of the route which should be updated
     * @param params  list of params that should be updated
     * @return updated route
     */
    @Override
    public RouteDTO patch(Long routeId, Map<String, Object> params) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        if (params.get("distance") != null) {
            route.setDistance(Double.valueOf(String.valueOf(params.get("distance"))));
        }
        if (params.get("departureTime") != null) {
            route.setDepartureTime(LocalDateTime.parse(String.valueOf(params.get("departureTime"))));
        }
        if (params.get("arrivalTime") != null) {
            route.setArrivalTime(LocalDateTime.parse(String.valueOf(params.get("arrivalTime"))));
        }
        if (params.get("price") != null) {
            route.setPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(params.get("price")))));
        }
        if (params.get("place") != null) {
            route.setPlace(String.valueOf(params.get("place")));
        }
        if (params.get("aircraftId") != null) {
            route.setAircraft(aircraftRepository.findById(Long.valueOf(String.valueOf(params.get("aircraftId"))))
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    ));
        }
        RouteDTO routeDTO = map(routeRepository.save(route), RouteDTO.class);
        routeDTO.setAircraftId(route.getAircraft().getAircraftId());
        return routeDTO;
    }

    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    @Override
    public RouteDTO delete(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );
        routeRepository.delete(route);
        return map(route, RouteDTO.class);
    }

    @Override
    public RouteDTO findOne(Long aLong) {
        return null;
    }

    @Override
    public List<RouteDTO> findAll() {
        return null;
    }

    @Override
    public Page<RouteDTO> findAllPaginated(int page, int size) {
        return null;
    }
}