package org.sergei.cargo.rest.dto.response;

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
public class ResponseWithMetadataDTO<T> implements Serializable {
    private static final long serialVersionUID = 4176344345954226364L;
    private ResponseDTO<T> generalResponse;
    private FacetCountDTO facetCount;
}
