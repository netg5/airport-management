package org.sergei.flightservice.exceptions;

import org.sergei.flightservice.dto.ApiErrorDTO;
import org.sergei.flightservice.dto.ErrorDetailsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author Sergei Visotsky, 2018
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected final ResponseEntity<ErrorDetailsDTO> handleNoRecordFoundException(RecordNotFoundException e,
                                                                                 WebRequest request) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected final ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                                    WebRequest request) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        String message = "Internal server error";
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiErrorDTO, new HttpHeaders(), apiErrorDTO.getStatus());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        String message = "Media type is not supported";
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiErrorDTO, new HttpHeaders(), apiErrorDTO.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        String message = "Media type is not acceptable";
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiErrorDTO, new HttpHeaders(), apiErrorDTO.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String message = "Method argument is not valid";
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiErrorDTO, new HttpHeaders(), apiErrorDTO.getStatus());
    }
}
