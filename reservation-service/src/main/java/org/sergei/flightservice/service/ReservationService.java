package org.sergei.flightservice.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.sergei.flightservice.util.ObjectMapperUtil.*;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationService implements IReservationService<ReservationExtendedDTO, ReservationDTO> {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";
    private static final String ROUTE_NOT_FOUND = "Route with this ID not found";
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private static final String RESERVATION_NOT_FOUND = "Reservation with this ID not found";
    private static final String RESERVATIONS_NOT_FOUND = "This customer has no reservations made";

    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private ReservationRepository reservationRepository;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public ReservationService(CustomerRepository customerRepository, RouteRepository routeRepository,
                              ReservationRepository reservationRepository, AircraftRepository aircraftRepository) {
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
    @Override
    public ReservationExtendedDTO findOneForCustomer(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        );

        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        ReservationExtendedDTO flightReservationExtendedDTO =
                map(reservation, ReservationExtendedDTO.class);
        flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

        // Find route by ID
        Route route = routeRepository.findById(reservation.getRoute().getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROUTE_NOT_FOUND)
                );
        RouteExtendedDTO routeExtendedDTO = map(route, RouteExtendedDTO.class);

        // Find aircraftDTO by ID taken from the entity
        Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);

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
    @Override
    public List<ReservationExtendedDTO> findAllForCustomer(Long customerId) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        List<Reservation> reservation = reservationRepository.findAllForCustomer(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATIONS_NOT_FOUND)
                );
        List<ReservationExtendedDTO> flightReservationExtendedDTOList =
                mapAll(reservation, ReservationExtendedDTO.class);
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
            RouteExtendedDTO routeExtendedDTO = map(route, RouteExtendedDTO.class);

            // Find aircraftDTO by ID taken from the entity
            Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);

            // Set aircraftDTO DTO to the flight reservation extended DTO
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);
            counter++;
        }

        // Returns extended flight reservation DTO
        return flightReservationExtendedDTOList;
    }


    /**
     * Find all reservation for customer paginated
     *
     * @param page page number
     * @param size number of elements ont he page
     * @return list of found entities
     */
    @Override
    public Page<ReservationExtendedDTO> findAllForCustomerPaginated(Long customerId, int page, int size) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        Page<Reservation> reservation = reservationRepository.findAllForCustomerPaginated(customerId, PageRequest.of(page, size))
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATIONS_NOT_FOUND)
                );
        Page<ReservationExtendedDTO> flightReservationExtendedDTOList =
                mapAllPages(reservation, ReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (ReservationExtendedDTO flightReservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
            flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeRepository.findById(reservation.getContent().get(counter).getRoute().getRouteId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(ROUTE_NOT_FOUND)
                    );
            RouteExtendedDTO routeExtendedDTO = map(route, RouteExtendedDTO.class);

            // Find aircraftDTO by ID taken from the entity
            Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);

            // Set aircraftDTO DTO to the flight reservation extended DTO
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);
            counter++;
        }

        // Returns extended flight reservation DTO
        return flightReservationExtendedDTOList;
    }

    /**
     * Method to save reservation for customer
     *
     * @param customerId     Gets customer ID
     * @param reservationDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    @Override
    public ReservationDTO saveReservation(Long customerId,
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

        final Long customerEntityId = customer.getCustomerId();
        final Long routeEntityId = route.getRouteId();

        // Set customer ID and route into the flight reservation DTO
        reservationDTO.setCustomerId(customerEntityId);
        reservationDTO.setRouteId(routeEntityId);

        Reservation reservation = map(reservationDTO, Reservation.class);
        reservation.setCustomer(customer);
        reservation.setRoute(route);

        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationDTO savedReservationDTO = map(savedReservation, ReservationDTO.class);
        savedReservationDTO.setCustomerId(customerEntityId);
        savedReservationDTO.setRouteId(routeEntityId);

        return savedReservationDTO;
    }

    /**
     * Update reservation details
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    @Override
    public ReservationDTO updateReservation(Long customerId, Long reservationId, Map<String, Object> params) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer or reservation with this ID not found")
                );
        reservation.setCustomer(customer);
        if (params.get("routeId") != null) {
            reservation.setRoute(routeRepository.findById(Long.valueOf(String.valueOf(params.get("routeId"))))
                    .orElseThrow(() ->
                            new ResourceNotFoundException(ROUTE_NOT_FOUND)
                    ));
        }
        if (params.get("reservationDate") != null) {
            reservation.setReservationDate(LocalDateTime.parse(String.valueOf(params.get("reservationDate"))));
        }
        ReservationDTO reservationDTO = map(reservationRepository.save(reservation), ReservationDTO.class);
        reservationDTO.setRouteId(reservation.getRoute().getRouteId());
        reservationDTO.setCustomerId(customer.getCustomerId());
        return reservationDTO;
    }


    /**
     * Delete reservation by ID and customer ID
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be deleted
     * @return deleted reservation DTO
     */
    @Override
    public ReservationExtendedDTO deleteReservation(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        );
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION_NOT_FOUND)
                );
        reservationRepository.deleteByCustomerIdAndReservationId(customer, reservation);
        return map(reservation, ReservationExtendedDTO.class);
    }

    @Override
    public Object findOne(Long aLong) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Page<ReservationExtendedDTO> findAllPaginated(int page, int size) {
        return null;
    }

    @Override
    public Object save(Object entityDTO) {
        return null;
    }

    @Override
    public Object update(Long aLong, Object entityDTO) {
        return null;
    }

    @Override
    public Object patch(Long aLong, Map params) {
        return null;
    }

    @Override
    public ReservationExtendedDTO delete(Long aLong) {
        return null;
    }
}
