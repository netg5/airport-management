package org.sergei.orchestration.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HangarDTO implements Serializable {
    private static final long serialVersionUID = 4795036446874569102L;
    private Long id;
    private String hangarNumber;
    private Integer capacity;
    private String hangarLocation;

    @Setter
    @JsonIgnoreProperties(value = "hangar")
    private List<AircraftDTO> aircraft;
}
