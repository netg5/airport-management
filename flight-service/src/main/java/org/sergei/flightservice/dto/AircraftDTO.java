package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO extends ResourceSupport {
    private Long aircraftId;
    private String model;
    private String aircraftName;
    private Double aircraftWeight;
    private Integer maxPassengers;
}