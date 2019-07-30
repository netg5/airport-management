package org.sergei.manager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PilotRequestDTO implements Serializable {
    private static final long serialVersionUID = -7115567348850961177L;
    private Long pilotId;
}
