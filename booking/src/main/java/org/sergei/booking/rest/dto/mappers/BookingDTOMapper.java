package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class BookingDTOMapper implements IMapper<Booking, BookingDTO> {

    private final FlightDTOMapper flightDTOMapper;
    private final PassengerDTOMapper passengerDTOMapper;

    @Autowired
    public BookingDTOMapper(FlightDTOMapper flightDTOMapper,
                            PassengerDTOMapper passengerDTOMapper) {
        this.flightDTOMapper = flightDTOMapper;
        this.passengerDTOMapper = passengerDTOMapper;
    }

    @Override
    public BookingDTO apply(Booking booking) {
        return BookingDTO.builder()
                .bookingId(booking.getId())
                .dateOfFlying(booking.getDateOfFlying())
                .departureTime(booking.getDepartureTime())
                .arrivalTime(booking.getArrivalTime())
                .hoursFlying(booking.getHoursFlying())
                .passenger(passengerDTOMapper.apply(booking.getPassenger()))
                .flightId(booking.getFlightId())
                .flyModeCode(booking.getFlyModeCode())
//                .flight(flightDTOMapper.apply(booking.getFlight()))
//                .flyMode(flyModeDTOMapper.apply(booking.getFlyMode()))
                .build();
    }
}
