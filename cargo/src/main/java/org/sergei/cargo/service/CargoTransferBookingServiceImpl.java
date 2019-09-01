package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.*;
import org.sergei.cargo.jpa.repository.*;
import org.sergei.cargo.rest.dto.mappers.CargoTransferBookingDTOMapper;
import org.sergei.cargo.rest.dto.request.CargoRequestDTO;
import org.sergei.cargo.rest.dto.request.CargoTransferBookingRequestDTO;
import org.sergei.cargo.rest.dto.response.CargoTransferBookingResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseErrorDTO;
import org.sergei.cargo.service.interfaces.CargoTransferBookingService;
import org.sergei.cargo.service.interfaces.ResponseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class CargoTransferBookingServiceImpl implements CargoTransferBookingService {

    @PersistenceContext
    private EntityManager em;

    private final ResponseMessageService responseMessageService;
    private final CargoTransferBookingRepository cargoTransferBookingRepository;
    private final CargoTransferFlightRepository cargoTransferFlightRepository;
    private final CargoRepository cargoRepository;
    private final SalesAgentRepository salesAgentRepository;
    private final WarehouseRepository warehouseRepository;
    private final CargoTransferBookingDTOMapper cargoTransferBookingDTOMapper;

    @Autowired
    public CargoTransferBookingServiceImpl(ResponseMessageService responseMessageService,
                                           CargoTransferBookingRepository cargoTransferBookingRepository,
                                           CargoTransferFlightRepository cargoTransferFlightRepository,
                                           CargoRepository cargoRepository,
                                           SalesAgentRepository salesAgentRepository,
                                           WarehouseRepository warehouseRepository,
                                           CargoTransferBookingDTOMapper cargoTransferBookingDTOMapper) {
        this.responseMessageService = responseMessageService;
        this.cargoTransferBookingRepository = cargoTransferBookingRepository;
        this.cargoTransferFlightRepository = cargoTransferFlightRepository;
        this.cargoRepository = cargoRepository;
        this.salesAgentRepository = salesAgentRepository;
        this.warehouseRepository = warehouseRepository;
        this.cargoTransferBookingDTOMapper = cargoTransferBookingDTOMapper;
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@link org.sergei.cargo.service.interfaces.CargoTransferBookingService#makeBooking(CargoTransferBookingRequestDTO)}
     */
    @Override
    public ResponseEntity<ResponseDTO<CargoTransferBookingResponseDTO>> makeBooking(CargoTransferBookingRequestDTO request) {
        CargoRequestDTO cargoRequest = request.getCargo();
        Double cargoWeight = cargoRequest.getWeight();
        Integer maxWeight = cargoRequest.getMaxGrossWeight();
        Double cargoHeight = cargoRequest.getHeight();
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

        // Save cargo data----------------------------------------
        SalesAgent salesAgent = salesAgentRepository.findSalesAgentByCode(cargoRequest.getSalesAgentCode());
        Warehouse warehouse = warehouseRepository.findWarehouseByCode(cargoRequest.getWarehouseCode());
        Cargo cargo = Cargo.builder()
                .weight(cargoRequest.getWeight())
                .unitType(cargoRequest.getUnitType())
                .tareWeight(cargoRequest.getTareWeight())
                .maxGrossWeight(cargoRequest.getMaxGrossWeight())
                .internalVolume(cargoRequest.getInternalVolume())
                .length(cargoRequest.getLength())
                .height(cargoRequest.getHeight())
                .salesAgent(salesAgent)
                .warehouse(warehouse)
                .build();
        Cargo savedCargo = cargoRepository.save(cargo);

        // Save transfer flight data----------------------------------------------------------
        CargoTransferFlight transferFlight =
                cargoTransferFlightRepository.getCargoTransferByCode(request.getTransferFlightCode());

        CargoTransferBooking cargoTransferBooking = CargoTransferBooking.builder()
                .hoursFlying(request.getHoursFlying())
                .dateOfFlying(request.getDateOfFlying())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .arrivalTime(request.getArrivalTime())
                .cargoTransferFlight(transferFlight)
                .cargo(savedCargo)
                .build();

        CargoTransferBooking savedCargoTransferBooking = cargoTransferBookingRepository.save(cargoTransferBooking);

        // Create response message -------------------------------------------
        CargoTransferBookingResponseDTO cargoTransferBookingResponseDTO =
                cargoTransferBookingDTOMapper.apply(savedCargoTransferBooking);
        ResponseDTO<CargoTransferBookingResponseDTO> response = ResponseDTO.<CargoTransferBookingResponseDTO>builder()
                .errorList(errorList)
                .response(List.of(cargoTransferBookingResponseDTO))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
