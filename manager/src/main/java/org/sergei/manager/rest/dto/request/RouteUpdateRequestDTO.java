package org.sergei.manager.rest.dto.request;

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
public class RouteUpdateRequestDTO implements Serializable {
    private static final long serialVersionUID = 1734896447103188392L;
    private Long routeId;
    private RouteRequestDTO routeRequest;
}
