package org.sergei.processor.service;

import org.sergei.processor.rest.dto.response.ResponseErrorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ResponseMessageService {

    /**
     * Get response message list by code
     *
     * @param code response message code
     * @return collection with all the errors
     */
    List<ResponseErrorDTO> responseErrorListByCode(String code);

}