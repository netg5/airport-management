package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.sergei.flightservice.model.Aircraft;

/**
 * @author Sergei Visotsky, 2018
 */
@JsonRootName("route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteExtendedDTO extends RouteDTO {

    @JsonProperty("aircraft")
    @Getter(onMethod = @__(@JsonIgnore))
    private Aircraft aircraft;
}
