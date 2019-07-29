package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationResponseDTO> {

    @Override
    public ReservationResponseDTO apply(Reservation reservation) {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservationResponseDTO.setCustomerId(reservation.getId());
        reservationResponseDTO.setDateOfFlying(reservation.getDateOfFlying());
        reservationResponseDTO.setDepartureTime(reservation.getDepartureTime());
        reservationResponseDTO.setArrivalTime(reservation.getArrivalTime());
        reservationResponseDTO.setHoursFlying(reservation.getHoursFlying());
        reservationResponseDTO.setAircraftId(reservation.getAircraft().getId());

        return reservationResponseDTO;
    }
}
