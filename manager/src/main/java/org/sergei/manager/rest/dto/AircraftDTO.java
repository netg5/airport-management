package org.sergei.manager.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO implements Serializable {
    private static final long serialVersionUID = -8398761845885572454L;
    private Long aircraftId;
    private String registrationNumber;
    private String modelNumber;
    private String aircraftName;
    private Integer capacity;
    private Double weight;
    private Integer exploitationPeriod;
    private Boolean available;
    @JsonIgnoreProperties(value = "aircraft")
    private HangarDTO hangar;
    private ManufacturerDTO manufacturer;
}