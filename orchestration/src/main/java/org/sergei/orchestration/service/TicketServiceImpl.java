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
public class TicketServiceImpl implements TicketService {

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTickets(Long passengerId, String currency) throws NotImplementedException {
        // TODO
        throw new NotImplementedException("Method is not implemented");
    }
}
