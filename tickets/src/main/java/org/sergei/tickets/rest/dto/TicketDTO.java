package org.sergei.tickets.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateOfFlying;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private String aircraftName;
    private String modelNumber;
}
