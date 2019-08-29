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
public class FacetFieldsDTO implements Serializable {
    private static final long serialVersionUID = -7883273212016506692L;
    private Long resNum;
}
