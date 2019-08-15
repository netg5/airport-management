package org.sergei.processor.jdbc.model;

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
public class ActualFlight implements Serializable {

    private static final long serialVersionUID = -6683080732212544539L;

    private Long actualFlightId;
    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String country;
    private String email;
    private String phone;
    private Long aircraftId;
    private Long pilotId;
    private Long routeId;
}
