package org.sergei.tickets.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private Long customerId;
    private String place;
    private Double distance;
}
