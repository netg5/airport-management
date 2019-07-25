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
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setCustomerId(ticket.getCustomerId());
        ticketDTO.setAircraftName(ticket.getAircraftName());
        ticketDTO.setFirstName(ticket.getFirstName());
        ticketDTO.setLastName(ticket.getLastName());
        ticketDTO.setDistance(ticket.getDistance());
        ticketDTO.setPlace(ticket.getPlace());
        ticketDTO.setRouteId(ticket.getRouteId());
        ticketDTO.setPrice(ticket.getPrice());

        return ticketDTO;
    }
}
