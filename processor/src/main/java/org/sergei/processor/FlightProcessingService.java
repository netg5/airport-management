package org.sergei.processor;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author Sergei Visotsky
 */
@Service
@EnableScheduling
public class FlightProcessingService {

    private static final Logger log = LoggerFactory.getLogger(FlightProcessingService.class);

    private final DataSource dataSource;
    private final Tracer tracer;

    @Autowired
    public FlightProcessingService(DataSource dataSource,
                                   Tracer tracer) {
        this.dataSource = dataSource;
        this.tracer = tracer;
    }

    @Scheduled(cron = "${cron.expression}")
    private void processFlight() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Span span = tracer.buildSpan("call flight_processor() started").start();
        jdbc.execute("call flight_processor()");
        span.finish();
        log.info("Procedure call flight_processor() executed...");
    }
}
