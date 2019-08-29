package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.SalesAgent;
import org.sergei.cargo.jpa.repository.SalesAgentRepository;
import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.rest.dto.mappers.SalesAgentDTOListMapper;
import org.sergei.cargo.rest.dto.response.FacetCountDTO;
import org.sergei.cargo.rest.dto.response.FacetFieldsDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.SalesAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class SalesAgentServiceImpl implements SalesAgentService {

    private final SalesAgentRepository salesAgentRepository;
    private final SalesAgentDTOListMapper salesAgentDTOListMapper;

    @Autowired
    public SalesAgentServiceImpl(SalesAgentRepository salesAgentRepository,
                                 SalesAgentDTOListMapper salesAgentDTOListMapper) {
        this.salesAgentRepository = salesAgentRepository;
        this.salesAgentDTOListMapper = salesAgentDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseWithMetadataDTO<SalesAgentDTO>> getAllSalesAgents(int page, int size) {
        Page<SalesAgent> salesAgents = salesAgentRepository.findAll(PageRequest.of(page, size));
        List<SalesAgentDTO> salesAgentDTOList = salesAgentDTOListMapper.apply(salesAgents.getContent());

        ResponseWithMetadataDTO<SalesAgentDTO> response =
                ResponseWithMetadataDTO.<SalesAgentDTO>builder()
                        .facetCount(FacetCountDTO.builder()
                                .facetFields(FacetFieldsDTO.builder()
                                        .resNum(salesAgents.getTotalElements())
                                        .build())
                                .build())
                        .generalResponse(ResponseDTO.<SalesAgentDTO>builder()
                                .errorList(List.of())
                                .response(salesAgentDTOList)
                                .build())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
