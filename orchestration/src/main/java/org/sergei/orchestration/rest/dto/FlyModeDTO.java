package org.sergei.orchestration.rest.dto;

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
public class FlyModeDTO implements Serializable {
    private static final long serialVersionUID = -7949191753690971099L;

    private String code;
    private String title;
    private String description;
    private List<PriceDTO> price;
}
