package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.sergei.cargo.jpa.repository.CargoTransferFlightRepository;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.rest.dto.mappers.CargoTransferFlightDTOListMapper;
import org.sergei.cargo.rest.dto.response.FacetCountDTO;
import org.sergei.cargo.rest.dto.response.FacetFieldsDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.CargoTransferFlightService;
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
public class CargoTransferFlightServiceImpl implements CargoTransferFlightService {

    private final CargoTransferFlightRepository cargoTransferFlightRepository;
    private final CargoTransferFlightDTOListMapper cargoTransferFlightDTOListMapper;

    public CargoTransferFlightServiceImpl(CargoTransferFlightRepository cargoTransferFlightRepository,
                                          CargoTransferFlightDTOListMapper cargoTransferFlightDTOListMapper) {
        this.cargoTransferFlightRepository = cargoTransferFlightRepository;
        this.cargoTransferFlightDTOListMapper = cargoTransferFlightDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseWithMetadataDTO<CargoTransferFlightDTO>> getAllCargoTransferFlights(int page, int size) {
        Page<CargoTransferFlight> cargoTransferFlightDTOPage =
                cargoTransferFlightRepository.findAll(PageRequest.of(page, size));

        List<CargoTransferFlightDTO> cargoTransferFlightDTOList =
                cargoTransferFlightDTOListMapper.apply(cargoTransferFlightDTOPage.getContent());

        ResponseWithMetadataDTO<CargoTransferFlightDTO> response =
                ResponseWithMetadataDTO.<CargoTransferFlightDTO>builder()
                        .generalResponse(ResponseDTO.<CargoTransferFlightDTO>builder()
                                .errorList(List.of())
                                .response(cargoTransferFlightDTOList)
                                .build())
                        .facetCount(FacetCountDTO.builder()
                                .facetFields(FacetFieldsDTO.builder()
                                        .resNum(cargoTransferFlightDTOPage.getTotalElements())
                                        .build())
                                .build())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
