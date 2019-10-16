package org.sergei.orchestration.service;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.sergei.orchestration.rest.dto.TicketDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface TicketService {
    /**
     * Find all tickets for passenger
     *
     * @param passengerId ID of the passenger
     * @param currency    currency of the ticket price
     * @return All ticket information for customer
     */
    ResponseEntity<ResponseDTO<TicketDTO>> findAllTickets(Long passengerId, String currency) throws NotImplementedException;
}
