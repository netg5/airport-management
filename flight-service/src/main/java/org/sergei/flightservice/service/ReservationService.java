package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.dto.ReservationDTO;
import org.sergei.flightservice.dto.ReservationExtendedDTO;
import org.sergei.flightservice.dto.RouteExtendedDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.model.Reservation;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.repository.ReservationRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ReservationService {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";
    private static final String ROUTE_NOT_FOUND = "Route with this ID not found";
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private static final String RESERVATION_NOT_FOUND = "Reservation with this ID not found";

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private ReservationRepository reservationRepository;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public ReservationService(ModelMapper modelMapper,
                              CustomerRepository customerRepository, RouteRepository routeRepository,
                              ReservationRepository reservationRepository, AircraftRepository aircraftRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.routeRepository = routeRepository;
        this.reservationRepository = reservationRepository;
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Method to get flight reservation for the customer by reservation ID
     *
     * @param customerId    gets customer ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    public ReservationExtendedDTO getOneForCustomerById(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        );

        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        ReservationExtendedDTO flightReservationExtendedDTO =
                modelMapper.map(reservation, ReservationExtendedDTO.class);
        flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

        // Find route by ID
        Route route = routeRepository.findById(reservation.getRoute().getRouteId())
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
    public List<ReservationExtendedDTO> getAllReservationsForCustomer(Long customerId) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        List<Reservation> reservation = reservationRepository.findAllForCustomer(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        List<ReservationExtendedDTO> flightReservationExtendedDTOList =
                ObjectMapperUtils.mapAll(reservation, ReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (ReservationExtendedDTO flightReservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
            flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeRepository.findById(reservation.get(counter).getRoute().getRouteId())
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
     * @param reservationDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    public ReservationDTO createReservation(Long customerId,
                                            ReservationDTO reservationDTO) {
        // Find customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find route by route ID
        Route route = routeRepository.findById(reservationDTO.getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );

        // Set customer ID and route into the flight reservation DTO
        reservationDTO.setCustomerId(customer.getCustomerId());
        reservationDTO.setRouteId(route.getRouteId());
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservationRepository.save(reservation);
        return reservationDTO;
    }

    /**
     * Method to update reservation details
     *
     * @param customerId           gets as a parameter customer ID
     * @param reservationId        get as a parameter reservation ID
     * @param reservationDTO get as a parameter Flight reservation DTO
     * @return flight reservation DTO
     */
    public ReservationDTO updateReservation(Long customerId,
                                            Long reservationId,
                                            ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer or reservation with this ID not found")
                );
        reservation.setCustomer(
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                        ));
        reservation.setRoute(
                routeRepository.findById(reservationDTO.getRouteId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(ROUTE_NOT_FOUND)
                        ));
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservationRepository.save(reservation);

        return reservationDTO;
    }

    /**
     * Delete reservation by ID
     *
     * @param reservationId get reservation ID to be deleted
     * @return deleted reservation DTO
     */
    public ReservationExtendedDTO delete(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        reservationRepository.delete(reservation);
        return modelMapper.map(reservation, ReservationExtendedDTO.class);
    }
}
