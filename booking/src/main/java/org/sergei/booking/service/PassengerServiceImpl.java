package org.sergei.booking.service;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.jpa.model.mapper.PassengerModelMapper;
import org.sergei.booking.jpa.repository.PassengerRepository;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.rest.dto.mappers.PassengerDTOListMapper;
import org.sergei.booking.rest.dto.mappers.PassengerDTOMapper;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.sergei.booking.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDTOMapper passengerDTOMapper;
    private final PassengerDTOListMapper passengerDTOListMapper;
    private final PassengerModelMapper passengerModelMapper;
    private final ResponseMessageService responseMessageService;
    private final Tracer tracer;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                PassengerDTOMapper passengerDTOMapper,
                                PassengerDTOListMapper passengerDTOListMapper,
                                PassengerModelMapper passengerModelMapper,
                                ResponseMessageService responseMessageService,
                                Tracer tracer) {
        this.passengerRepository = passengerRepository;
        this.passengerDTOMapper = passengerDTOMapper;
        this.passengerDTOListMapper = passengerDTOListMapper;
        this.passengerModelMapper = passengerModelMapper;
        this.responseMessageService = responseMessageService;
        this.tracer = tracer;
    }

    /**
     * Find all passengers with pagination
     *
     * @param page how many pages to show
     * @param size number of elements per page
     * @return list with entities
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerDTO>> findAllPassengers(int page, int size) {
        Page<Passenger> passengersPage = passengerRepository.findAll(PageRequest.of(page, size));
        if (passengersPage.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<PassengerDTO> passengerDTOList = passengerDTOListMapper.apply(passengersPage.getContent());
            ResponseDTO<PassengerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(passengerDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find passenger by ID
     *
     * @param passengerId get passenger ID as a parameter
     * @return passenger
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerDTO>> findPassengerById(Long passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            PassengerDTO passengerDTO = passengerDTOMapper.apply(passenger.get());
            ResponseDTO<PassengerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(passengerDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to update customerDTO details
     *
     * @param request with passenger ID and cutomer DTO
     * @return response with passenger DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerDTO>> update(PassengerDTO request) {

        Long passengerId = request.getPassengerId();

        Span span = tracer.buildSpan("passengerRepository.findById() started").start();
        span.setTag("passengerId", passengerId);

        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        span.finish();
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Passenger updatedPassenger = passengerModelMapper.apply(request);

            Span span1 = tracer.buildSpan("passengerRepository.save() to update starting....").start();
            Passenger saveUpdatedPassenger = passengerRepository.save(updatedPassenger);
            span1.log("passengerRepository.save() to update completed");
            span1.finish();
            PassengerDTO passengerDTOResp = passengerDTOMapper.apply(saveUpdatedPassenger);

            ResponseDTO<PassengerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(passengerDTOResp));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }
}
