package org.sergei.processor.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 2914359527061574119L;

    private Long id;
    private String manufacturerCode;
    private String manufacturerName;
    private String location;

}
