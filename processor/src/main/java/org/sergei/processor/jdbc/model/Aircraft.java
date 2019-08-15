package org.sergei.processor.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft implements Serializable {

    private static final long serialVersionUID = -155783393887085614L;

    private Long id;
    private String registrationNumber;
    private String modelNumber;
    private String aircraftName;
    private Integer capacity;
    private Double weight;
    private Integer exploitationPeriod;
    private Integer available;
    private Manufacturer manufacturer;
    private Hangar hangar;
}
