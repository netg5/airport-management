package org.sergei.auth.service.response;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError implements Serializable {

    private static final long serialVersionUID = 7356941682270970714L;

    private String errorCode;
    private String errorDescription;
    private String errorType;
}
