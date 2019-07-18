package org.sergei.reservation.rest.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO<T> {
    private List<ResponseErrorDTO> errorList;
    private List<T> response;
}
