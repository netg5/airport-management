package org.sergei.tickets.rest.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO implements Serializable {

    private static final long serialVersionUID = 4746464867827649037L;

    private Long passengerId;
    private String firstName;
    private String lastName;
    private Long aircraftId;
    private LocalDateTime dateOfFlying;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private String aircraftName;
    private String model;
}
