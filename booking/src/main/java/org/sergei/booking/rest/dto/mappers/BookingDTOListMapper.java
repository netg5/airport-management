package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class BookingDTOListMapper implements IMapper<List<Booking>, List<BookingDTO>> {

    private final BookingDTOMapper bookingDTOMapper;

    @Autowired
    public BookingDTOListMapper(BookingDTOMapper bookingDTOMapper) {
        this.bookingDTOMapper = bookingDTOMapper;
    }

    @Override
    public List<BookingDTO> apply(List<Booking> bookings) {
        return bookingDTOMapper.applyList(bookings);
    }
}
