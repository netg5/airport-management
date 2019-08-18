package org.sergei.auth.service.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 8436537119496286158L;

    private List<ResponseError> errorList;
    private List<T> response;
}
