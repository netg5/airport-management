package org.sergei.processor.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
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
    @JsonIgnoreProperties(value = "aircraft")
    private HangarDTO hangar;
    private ManufacturerDTO manufacturer;
}