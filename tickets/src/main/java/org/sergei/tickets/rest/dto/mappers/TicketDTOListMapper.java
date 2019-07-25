package org.sergei.tickets.rest.dto.mappers;

import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.rest.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class TicketDTOListMapper implements IMapper<List<Ticket>, List<TicketDTO>> {

    private final TicketDTOMapper ticketDTOMapper;

    @Autowired
    public TicketDTOListMapper(TicketDTOMapper ticketDTOMapper) {
        this.ticketDTOMapper = ticketDTOMapper;
    }

    @Override
    public List<TicketDTO> apply(List<Ticket> tickets) {
        return ticketDTOMapper.applyList(tickets);
    }
}
