package org.sergei.cargo.rest.dto.request;

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
public class CargoTransferBookingRequestDTO implements Serializable {
    private static final long serialVersionUID = 5251906944740203347L;

    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private String transferFlightCode;
    private CargoRequestDTO cargo;
}
