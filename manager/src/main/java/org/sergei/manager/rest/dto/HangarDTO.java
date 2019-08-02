package org.sergei.manager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HangarDTO {
    private Long id;
    private String hangarNumber;
    private Integer capacity;
    private String hangarLocation;
}
