package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationResponseDTO> {

    private final PassengerDTOMapper passengerDTOMapper;
    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public ReservationDTOMapper(PassengerDTOMapper passengerDTOMapper,
                                AircraftDTOMapper aircraftDTOMapper) {
        this.passengerDTOMapper = passengerDTOMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public ReservationResponseDTO apply(Reservation reservation) {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservationResponseDTO.setId(reservation.getId());
        reservationResponseDTO.setDateOfFlying(reservation.getDateOfFlying());
        reservationResponseDTO.setDepartureTime(reservation.getDepartureTime());
        reservationResponseDTO.setArrivalTime(reservation.getArrivalTime());
        reservationResponseDTO.setHoursFlying(reservation.getHoursFlying());
        reservationResponseDTO.setAircraft(aircraftDTOMapper.apply(reservation.getAircraft()));
        reservationResponseDTO.setPassenger(passengerDTOMapper.apply(reservation.getPassenger()));

        return reservationResponseDTO;
    }
}
