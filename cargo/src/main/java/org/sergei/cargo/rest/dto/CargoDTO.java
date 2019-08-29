package org.sergei.cargo.rest.dto;

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
public class CargoDTO implements Serializable {
    private static final long serialVersionUID = 8558812785377374386L;

    private String unitType;
    private Double internalVolume;
    private Double length;
    private Double weight;
    private Double height;
    private Integer maxGrossWeight;
    private Integer tareWeight;
    private WarehouseDTO warehouse;
    private SalesAgentDTO salesAgent;
}
