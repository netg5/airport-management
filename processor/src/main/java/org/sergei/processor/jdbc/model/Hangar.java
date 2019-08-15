package org.sergei.processor.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hangar implements Serializable {

    private static final long serialVersionUID = 3403853570444000375L;

    private Long id;
    private String hangarNumber;
    private Integer capacity;
    private String hangarLocation;
    private List<Aircraft> aircrafts;
}
