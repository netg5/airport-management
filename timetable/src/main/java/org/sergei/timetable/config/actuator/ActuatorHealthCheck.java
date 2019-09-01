package org.sergei.timetable.config.actuator;

import org.sergei.template.rest.exceptions.FlightRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Actuator health check information for "/actuator/info" endpoint
 *
 * @author Sergei Visotsky
 */
class ActuatorHealthCheck {

    /**
     * Gets application health information from the file
     *
     * @return pojo with application information
     */
    static ActuatorHealthCheckResponse getAppInfo() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("info.properties")) {

            properties.load(inputStream);

            return ActuatorHealthCheckResponse.builder()
                    .appName(properties.getProperty("app.name"))
                    .appVersion(properties.getProperty("app.version"))
                    .appDescription(properties.getProperty("app.description"))
                    .build();
        } catch (IOException e) {
            throw new FlightRuntimeException("Unable to read application info from file", e);
        }
    }

}