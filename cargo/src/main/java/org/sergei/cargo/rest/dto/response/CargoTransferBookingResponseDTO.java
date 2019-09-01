package org.sergei.cargo.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sergei.cargo.rest.dto.CargoDTO;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoTransferBookingResponseDTO implements Serializable {
    private static final long serialVersionUID = 3219151122855842040L;

    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private CargoDTO cargo;
    private CargoTransferFlightDTO cargoTransferFlight;
}
