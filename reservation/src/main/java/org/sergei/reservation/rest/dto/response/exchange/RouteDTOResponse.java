package org.sergei.reservation.rest.dto.response.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTOResponse {

    @JsonProperty("errorList")
    private List<ResponseErrorDTO> errorList;

    @JsonProperty("response")
    private List<RouteDTO> response;

}
