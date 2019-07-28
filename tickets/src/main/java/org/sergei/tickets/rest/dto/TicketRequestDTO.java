package org.sergei.tickets.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO implements Serializable {
    private static final long serialVersionUID = -5198283871926597190L;
    private Long passengerId;
    private String place;
    private Double distance;
}
