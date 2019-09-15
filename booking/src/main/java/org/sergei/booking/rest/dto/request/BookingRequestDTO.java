package org.sergei.booking.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sergei.booking.rest.dto.PassengerDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO implements Serializable {

    private static final long serialVersionUID = -5502910005749994943L;

    private Long bookingId;
    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private PassengerDTO passenger;
    private Long flightId;
    private String flyModeCode;

}
