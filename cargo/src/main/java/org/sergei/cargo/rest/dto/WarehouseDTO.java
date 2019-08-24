package org.sergei.cargo.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class WarehouseDTO implements Serializable {
    private static final long serialVersionUID = 1391130563395767796L;

    @JsonIgnore
    private Long id;
    private String country;
    private String city;
    private String warehouseHanding;
    private Double heavyCargo;
    private String dangerous;
    private String keepCoolService;
    private String liveAnimals;
    private String valuableCargo;
}
