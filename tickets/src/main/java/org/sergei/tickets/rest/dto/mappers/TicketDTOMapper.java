package org.sergei.tickets.rest.dto.mappers;

import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.rest.dto.TicketDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class TicketDTOMapper implements IMapper<Ticket, TicketDTO> {

    @Override
    public TicketDTO apply(Ticket ticket) {
        return TicketDTO.builder()
                .firstName(ticket.getFirstName())
                .lastName(ticket.getLastName())
                .dateOfFlying(ticket.getDateOfFlying())
                .departureTime(ticket.getDepartureTime())
                .arrivalTime(ticket.getArrivalTime())
                .distance(ticket.getDistance())
                .place(ticket.getPlace())
                .title(ticket.getTitle())
                .amount(ticket.getAmount())
                .currency(ticket.getCurrency())
                .build();
    }
}
