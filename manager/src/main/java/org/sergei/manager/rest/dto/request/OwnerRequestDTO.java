package org.sergei.manager.rest.dto.request;

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
public class OwnerRequestDTO implements Serializable {
    private static final long serialVersionUID = -1687321829409003892L;
    private Long ownerId;
}
