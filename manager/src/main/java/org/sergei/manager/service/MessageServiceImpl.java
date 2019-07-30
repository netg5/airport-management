package org.sergei.manager.service;

import org.sergei.manager.jpa.model.ResponseMessage;
import org.sergei.manager.jpa.repository.MessageRepository;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<ResponseErrorDTO> responseErrorListByCode(String code) {
        List<ResponseMessage> responseMessageList = messageRepository.findResponseMessageByCode(code);

        List<ResponseErrorDTO> responseErrorList = new ArrayList<>();
        for (ResponseMessage responseMessage : responseMessageList) {
            responseErrorList.add(new ResponseErrorDTO(responseMessage.getCode(), responseMessage.getDescription(), "Error"));
        }

        return responseErrorList;
    }

}
