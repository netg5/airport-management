package org.sergei.reservationservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Route", description = "Route meta data model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO extends ResourceSupport {

    @ApiModelProperty("Route ID")
    private Long routeId;

    @ApiModelProperty("Route distance")
    private Double distance;

    @ApiModelProperty("Flight departure time")
    private LocalDateTime departureTime;

    @ApiModelProperty("Flight arrival time")
    private LocalDateTime arrivalTime;

    @ApiModelProperty("Flight price")
    private BigDecimal price;

    @ApiModelProperty("Place to fly")
    private String place;

    @ApiModelProperty("Aircraft ID which goes by this flight")
    private Long aircraftId;
}
