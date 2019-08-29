package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.CargoTransferBooking;
import org.sergei.cargo.jpa.model.mappers.CargoTransferBookingModelMapper;
import org.sergei.cargo.jpa.repository.CargoTransferBookingRepository;
import org.sergei.cargo.rest.dto.CargoTransferBookingDTO;
import org.sergei.cargo.rest.dto.mappers.CargoTransferBookingDTOMapper;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseErrorDTO;
import org.sergei.cargo.service.interfaces.CargoTransferBookingService;
import org.sergei.cargo.service.interfaces.ResponseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class CargoTransferBookingServiceImpl implements CargoTransferBookingService {

    private final ResponseMessageService responseMessageService;
    private final CargoTransferBookingRepository cargoTransferBookingRepository;
    private final CargoTransferBookingModelMapper cargoTransferBookingModelMapper;
    private final CargoTransferBookingDTOMapper cargoTransferFlightDTOMapper;

    @Autowired
    public CargoTransferBookingServiceImpl(ResponseMessageService responseMessageService,
                                           CargoTransferBookingRepository cargoTransferBookingRepository,
                                           CargoTransferBookingModelMapper cargoTransferBookingModelMapper,
                                           CargoTransferBookingDTOMapper cargoTransferFlightDTOMapper) {
        this.responseMessageService = responseMessageService;
        this.cargoTransferBookingRepository = cargoTransferBookingRepository;
        this.cargoTransferBookingModelMapper = cargoTransferBookingModelMapper;
        this.cargoTransferFlightDTOMapper = cargoTransferFlightDTOMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<CargoTransferBookingDTO>> makeBooking(CargoTransferBookingDTO request) {
        Double cargoWeight = request.getCargo().getWeight();
        Integer maxWeight = request.getCargo().getMaxGrossWeight();
        Double cargoHeight = request.getCargo().getHeight();
        List<ResponseErrorDTO> errorList = new ArrayList<>();
        if (cargoWeight >= maxWeight) {
            List<ResponseErrorDTO> weightError = responseMessageService.responseErrorListByCode("CRG_001");
            errorList.add(ResponseErrorDTO.builder()
                    .errorCode(weightError.get(0).getErrorCode())
                    .errorDescription(weightError.get(0).getErrorDescription())
                    .errorType("ERROR")
                    .build());
        } else if (cargoHeight >= 150) {
            List<ResponseErrorDTO> weightError = responseMessageService.responseErrorListByCode("CRG_002");
            errorList.add(ResponseErrorDTO.builder()
                    .errorCode(weightError.get(0).getErrorCode())
                    .errorDescription(weightError.get(0).getErrorDescription())
                    .errorType("ERROR")
                    .build());
        }
        CargoTransferBooking cargoTransferBooking = cargoTransferBookingModelMapper.apply(request);
        CargoTransferBooking cargoTransferBookingSaved = cargoTransferBookingRepository.save(cargoTransferBooking);
        CargoTransferBookingDTO cargoTransferBookingDTOSaved = cargoTransferFlightDTOMapper.apply(cargoTransferBookingSaved);

        ResponseDTO<CargoTransferBookingDTO> response = ResponseDTO.<CargoTransferBookingDTO>builder()
                .errorList(errorList)
                .response(List.of(cargoTransferBookingDTOSaved))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
