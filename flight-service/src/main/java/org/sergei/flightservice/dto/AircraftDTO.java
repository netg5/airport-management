package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO extends ResourceSupport {
    private Long aircraftId;
    private String model;
    private String aircraftName;
    private Double aircraftWeight;
    private Integer maxPassengers;
}