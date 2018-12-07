package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Route", description = "Route meta data model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO extends ResourceSupport {
    private Long routeId;
    private Double distance;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private String place;
    private Long aircraftId;
}
