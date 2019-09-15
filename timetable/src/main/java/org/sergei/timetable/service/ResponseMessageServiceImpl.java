package org.sergei.timetable.service;

import org.sergei.timetable.jpa.model.ResponseMessage;
import org.sergei.timetable.jpa.repository.ResponseMessageRepository;
import org.sergei.timetable.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ResponseMessageServiceImpl implements ResponseMessageService {

    private final ResponseMessageRepository responseMessageRepository;

    @Autowired
    public ResponseMessageServiceImpl(ResponseMessageRepository responseMessageRepository) {
        this.responseMessageRepository = responseMessageRepository;
    }

    @Override
    public List<ResponseErrorDTO> responseErrorListByCode(String code) {
        List<ResponseMessage> responseMessageList = responseMessageRepository.findResponseMessageByCode(code);
        List<ResponseErrorDTO> responseErrorList = new ArrayList<>();
        for (ResponseMessage responseMessage : responseMessageList) {
            ResponseErrorDTO responseErrorDTO = ResponseErrorDTO.builder()
                    .errorCode(responseMessage.getCode())
                    .errorDescription(responseMessage.getDescription())
                    .errorType("ERROR")
                    .build();
            responseErrorList.add(responseErrorDTO);
        }
        return responseErrorList;
    }

}
