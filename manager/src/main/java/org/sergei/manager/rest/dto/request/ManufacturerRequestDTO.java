package org.sergei.manager.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequestDTO implements Serializable {
    private static final long serialVersionUID = -7124425609086060832L;
    private String manufacturerCode;
}
