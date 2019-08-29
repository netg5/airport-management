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
public class FacetCountDTO implements Serializable {
    private static final long serialVersionUID = 27339797014415148L;
    private FacetFieldsDTO facetFields;
}
