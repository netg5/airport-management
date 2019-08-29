package org.sergei.cargo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoTransferBookingDTO implements Serializable {
    private static final long serialVersionUID = 3219151122855842040L;

    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private CargoDTO cargo;
    private CargoTransferFlightDTO cargoTransferFlight;
}
