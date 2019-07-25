package org.sergei.tickets.rest.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseErrorDTO implements Serializable {

    private static final long serialVersionUID = 7356941682270970714L;

    private String errorCode;
    private String errorDescription;
    private String errorType;
}
