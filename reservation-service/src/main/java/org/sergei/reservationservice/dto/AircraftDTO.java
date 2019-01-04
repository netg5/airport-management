package org.sergei.reservationservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO extends ResourceSupport {

    @ApiModelProperty("Aircraft ID")
    private Long aircraftId;

    @ApiModelProperty("Aircraft model")
    private String model;

    @ApiModelProperty("Aircraft name")
    private String aircraftName;

    @ApiModelProperty("Aircraft name")
    private Double aircraftWeight;

    @ApiModelProperty("Maximum passengers in aircraft")
    private Integer maxPassengers;
}