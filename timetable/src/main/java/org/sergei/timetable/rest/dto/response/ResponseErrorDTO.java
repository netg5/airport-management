package org.sergei.timetable.rest.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO implements Serializable {

    private static final long serialVersionUID = 7356941682270970714L;

    private String errorCode;
    private String errorDescription;
    private String errorType;
}
