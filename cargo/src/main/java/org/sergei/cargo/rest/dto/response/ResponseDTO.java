package org.sergei.cargo.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 8436537119496286158L;

    private List<ResponseErrorDTO> errorList;
    private List<T> response;
}
