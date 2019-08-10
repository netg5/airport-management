package org.sergei.reservation.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sergei.reservation.rest.dto.RouteDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTOExchangeResponse implements Serializable {

    private static final long serialVersionUID = 6472052003214476644L;

    @JsonProperty("errorList")
    private List<ResponseErrorDTO> errorList;

    @JsonProperty("response")
    private List<RouteDTO> response;

}
