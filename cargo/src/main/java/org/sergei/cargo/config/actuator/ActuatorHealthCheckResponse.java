package org.sergei.cargo.config.actuator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Actuator health check information for "/actuator/info" endpoint
 *
 * @author Sergei Visotsky
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ActuatorHealthCheckResponse implements Serializable {
    private static final long serialVersionUID = 2679670767651394648L;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date = new Date();
    private String appName;
    private String appVersion;
    private String appDescription;
}
