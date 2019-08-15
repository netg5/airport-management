package org.sergei.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author Sergei Visotsky
 */
@Repository
@EnableScheduling
public class FlightProcessing {

    private static final Logger log = LoggerFactory.getLogger(FlightProcessing.class);

    private final DataSource dataSource;

    @Autowired
    public FlightProcessing(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(cron = "${cron.expression}")
    public void processFlight() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("call flight_processor()");
        log.info("Procedure executed");
    }
}
