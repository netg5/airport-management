package org.sergei.cargo.rest.dto.request;

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
public class CargoRequestDTO implements Serializable {
    private static final long serialVersionUID = 6569597144317591588L;

    private String unitType;
    private Double internalVolume;
    private Double length;
    private Double weight;
    private Double height;
    private Integer maxGrossWeight;
    private Integer tareWeight;
    private String warehouseCode;
    private String salesAgentCode;
}
