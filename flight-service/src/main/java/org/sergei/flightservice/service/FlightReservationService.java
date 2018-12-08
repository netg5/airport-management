package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.dto.FlightReservationDTO;
import org.sergei.flightservice.dto.FlightReservationExtendedDTO;
import org.sergei.flightservice.dto.RouteExtendedDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.model.FlightReservation;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.repository.FlightReservationRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class FlightReservationService {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";
    private static final String ROUTE_NOT_FOUND = "Route with this ID not found";
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private static final String RESERVATION_NOT_FOUND = "Reservation with this ID not found";

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private FlightReservationRepository flightReservationRepository;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public FlightReservationService(ModelMapper modelMapper,
                                    CustomerRepository customerRepository, RouteRepository routeRepository,
                                    FlightReservationRepository flightReservationRepository, AircraftRepository aircraftRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.routeRepository = routeRepository;
        this.flightReservationRepository = flightReservationRepository;
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Method to get flight reservation for the customer by reservation ID
     *
     * @param customerId    gets customer ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    public FlightReservationExtendedDTO getOneForCustomerById(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        );

        FlightReservation flightReservation = flightReservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        FlightReservationExtendedDTO flightReservationExtendedDTO =
                modelMapper.map(flightReservation, FlightReservationExtendedDTO.class);
        flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

        // Find route by ID
        Route route = routeRepository.findById(flightReservation.getRoute().getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );
        RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

        // Find aircraftDTO by ID taken from the entity
        Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        AircraftDTO aircraftDTO = modelMapper.map(aircraft, AircraftDTO.class);

        // Set aircraft DTO to the flight reservation extended DTO
        routeExtendedDTO.setAircraftDTO(aircraftDTO);
        flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);

        return flightReservationExtendedDTO;
    }

    /**
     * Method to get all flight reservations for the customer
     *
     * @param customerId gets customer ID
     * @return extended flight reservation DTO
     */
    public List<FlightReservationExtendedDTO> getAllReservationsForCustomer(Long customerId) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        List<FlightReservation> flightReservation = flightReservationRepository.findAllForCustomer(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        List<FlightReservationExtendedDTO> flightReservationExtendedDTOList =
                ObjectMapperUtils.mapAll(flightReservation, FlightReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (FlightReservationExtendedDTO flightReservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
            flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeRepository.findById(flightReservation.get(counter).getRoute().getRouteId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(ROUTE_NOT_FOUND)
                    );
            RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

            // Find aircraftDTO by ID taken from the entity
            Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = modelMapper.map(aircraft, AircraftDTO.class);

            // Set aircraftDTO DTO to the flight reservation extended DTO
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);
            counter++;
        }

        // Returns extended flight reservation DTO
        return flightReservationExtendedDTOList;
    }

    /**
     * Method to create reservation for customer
     *
     * @param customerId           Gets customer ID
     * @param flightReservationDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    public FlightReservationDTO createReservationForCustomer(Long customerId,
                                                             FlightReservationDTO flightReservationDTO) {
        // Find customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find route by route ID
        Route route = routeRepository.findById(flightReservationDTO.getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );

        // Set customer ID and route into the flight reservation DTO
        flightReservationDTO.setCustomerId(customer.getCustomerId());
        flightReservationDTO.setRouteId(route.getRouteId());
        FlightReservation flightReservation = modelMapper.map(flightReservationDTO, FlightReservation.class);
        flightReservationRepository.save(flightReservation);
        return flightReservationDTO;
    }

    /**
     * Method to update reservation details
     *
     * @param customerId           gets as a parameter customer ID
     * @param reservationId        get as a parameter reservation ID
     * @param flightReservationDTO get as a parameter Flight reservation DTO
     * @return flight reservation DTO
     */
    public FlightReservationDTO updateReservationForCustomer(Long customerId,
                                                             Long reservationId,
                                                             FlightReservationDTO flightReservationDTO) {
        FlightReservation flightReservation = flightReservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer or reservation with this ID not found")
                );
        flightReservation.setCustomer(
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                        ));
        flightReservation.setRoute(
                routeRepository.findById(flightReservationDTO.getRouteId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(ROUTE_NOT_FOUND)
                        ));
        flightReservation.setReservationDate(flightReservationDTO.getReservationDate());
        flightReservationRepository.save(flightReservation);

        return flightReservationDTO;
    }

    /**
     * Delete reservation by ID
     *
     * @param reservationId get reservation ID to be deleted
     * @return deleted reservation DTO
     */
    public FlightReservationExtendedDTO delete(Long reservationId) {
        FlightReservation flightReservation = flightReservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        flightReservationRepository.delete(flightReservation);
        return modelMapper.map(flightReservation, FlightReservationExtendedDTO.class);
    }
}
