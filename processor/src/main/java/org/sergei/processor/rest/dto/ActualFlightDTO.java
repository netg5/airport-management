package org.sergei.processor.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sergei.processor.jdbc.model.Aircraft;
import org.sergei.processor.jdbc.model.Pilot;
import org.sergei.processor.jdbc.model.Route;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualFlightDTO implements Serializable {

    private static final long serialVersionUID = -6491852897584614129L;

    private Date dateOfFlying;
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
    private Aircraft aircraft;
    private Pilot pilot;
    private Route route;
}
