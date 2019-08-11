package org.sergei.manager.rest.dto;

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
public class AirportContactDTO implements Serializable {
    private static final long serialVersionUID = -2121839406646357835L;
    private String contactName;
    private String contactJob;
}
