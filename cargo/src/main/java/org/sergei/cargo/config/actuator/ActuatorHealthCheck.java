package org.sergei.cargo.config.actuator;

import org.sergei.cargo.rest.exceptions.FlightRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Actuator health check information for "/actuator/info" endpoint
 *
 * @author Sergei Visotsky
 */
public class ActuatorHealthCheck {

    public static ActuatorHealthCheckResponse getAppInfo() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream = classLoader.getResourceAsStream("info.properties")) {

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
