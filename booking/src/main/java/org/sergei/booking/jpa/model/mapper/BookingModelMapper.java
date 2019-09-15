package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class BookingModelMapper implements IMapper<BookingDTO, Booking> {

    private final PassengerModelMapper passengerModelMapper;
    private final FlightModelMapper flightModelMapper;
    private final FlyModeModelMapper flyModeModelMapper;

    @Autowired
    public BookingModelMapper(PassengerModelMapper passengerModelMapper,
                              FlightModelMapper flightModelMapper,
                              FlyModeModelMapper flyModeModelMapper) {
        this.passengerModelMapper = passengerModelMapper;
        this.flightModelMapper = flightModelMapper;
        this.flyModeModelMapper = flyModeModelMapper;
    }

    @Override
    public Booking apply(BookingDTO bookingDTO) {
        return Booking.builder()
                .departureTime(bookingDTO.getDepartureTime())
                .arrivalTime(bookingDTO.getArrivalTime())
                .dateOfFlying(bookingDTO.getDateOfFlying())
                .hoursFlying(bookingDTO.getHoursFlying())
                .passenger(passengerModelMapper.apply(bookingDTO.getPassenger()))
                .flight(flightModelMapper.apply(bookingDTO.getFlight()))
                .flyMode(flyModeModelMapper.apply(bookingDTO.getFlyMode()))
                .build();
    }
}
