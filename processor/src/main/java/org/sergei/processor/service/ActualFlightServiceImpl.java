package org.sergei.processor.service;

import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.jdbc.dao.ActualFlightDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
@EnableScheduling
public class ActualFlightServiceImpl implements ActualFlightService {

    private final ActualFlightDAO actualFlightDAO;

    @Autowired
    public ActualFlightServiceImpl(ActualFlightDAO actualFlightDAO) {
        this.actualFlightDAO = actualFlightDAO;
    }

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void processFlights() {
        actualFlightDAO.saveActualFlight();
    }
}
