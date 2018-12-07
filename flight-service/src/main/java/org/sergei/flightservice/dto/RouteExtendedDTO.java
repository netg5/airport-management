package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.sergei.flightservice.model.Aircraft;

/**
 * @author Sergei Visotsky, 2018
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("route")
public class RouteExtendedDTO extends RouteDTO {

    @JsonProperty("aircraft")
    private Aircraft aircraft;
}
