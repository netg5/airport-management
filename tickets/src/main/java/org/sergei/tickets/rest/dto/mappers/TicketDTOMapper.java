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

        ticketDTO.setPassengerId(ticket.getPassengerId());
        ticketDTO.setAircraftName(ticket.getAircraftName());
        ticketDTO.setFirstName(ticket.getFirstName());
        ticketDTO.setLastName(ticket.getLastName());
        ticketDTO.setDateOfFlying(ticket.getDateOfFlying());
        ticketDTO.setArrivalTime(ticket.getArrivalTime());
        ticketDTO.setHoursFlying(ticket.getHoursFlying());
        ticketDTO.setAircraftName(ticket.getAircraftName());
        ticketDTO.setModel(ticket.getModel());

        return ticketDTO;
    }
}
