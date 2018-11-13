/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightservice.dto;

import org.springframework.http.HttpStatus;

/**
 * @author Sergei Visotsky, 2018
 */
public class ApiErrorDTO {
    private HttpStatus status;
    private String message;
    private String error;

    public ApiErrorDTO() {
    }

    public ApiErrorDTO(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
