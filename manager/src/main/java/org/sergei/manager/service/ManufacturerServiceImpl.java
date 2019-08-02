package org.sergei.manager.service;

import org.sergei.manager.jpa.model.Manufacturer;
import org.sergei.manager.jpa.model.mappers.ManufacturerModelMapper;
import org.sergei.manager.jpa.repository.ManufacturerRepository;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.rest.dto.mappers.ManufacturerDTOListMapper;
import org.sergei.manager.rest.dto.mappers.ManufacturerDTOMapper;
import org.sergei.manager.rest.dto.request.ManufacturerRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final MessageService messageService;
    private final ManufacturerModelMapper manufacturerModelMapper;
    private final ManufacturerDTOMapper manufacturerDTOMapper;
    private final ManufacturerDTOListMapper manufacturerDTOListMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   MessageService messageService,
                                   ManufacturerModelMapper manufacturerModelMapper,
                                   ManufacturerDTOMapper manufacturerDTOMapper,
                                   ManufacturerDTOListMapper manufacturerDTOListMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.messageService = messageService;
        this.manufacturerModelMapper = manufacturerModelMapper;
        this.manufacturerDTOMapper = manufacturerDTOMapper;
        this.manufacturerDTOListMapper = manufacturerDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> findAll() {
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        if (manufacturerList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("MAN-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<ManufacturerDTO> manufacturerDTOList = manufacturerDTOListMapper.apply(manufacturerList);

            ResponseDTO<ManufacturerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(manufacturerDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> findByCode(ManufacturerRequestDTO request) {
        String manufacturerCode = request.getManufacturerCode();
        if (manufacturerCode == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Manufacturer> manufacturer = manufacturerRepository.findByCode(manufacturerCode);
            if (manufacturer.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("MAN-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                ManufacturerDTO manufacturerDTO = manufacturerDTOMapper.apply(manufacturer.get());

                ResponseDTO<ManufacturerDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(manufacturerDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> saveManufacturer(ManufacturerDTO request) {
        Manufacturer manufacturer = manufacturerModelMapper.apply(request);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        ManufacturerDTO manufacturerResponse = manufacturerDTOMapper.apply(savedManufacturer);
        return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(manufacturerResponse)), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> updateManufacturer(ManufacturerDTO request) {
        String code = request.getManufacturerCode();
        if (code == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Manufacturer> manufacturer = manufacturerRepository.findByCode(code);
            if (manufacturer.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("MAN-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Manufacturer updatedManufacturer = manufacturerModelMapper.apply(request);
                Manufacturer savedManufacturer = manufacturerRepository.save(updatedManufacturer);
                ManufacturerDTO manufacturerDTOResponse = manufacturerDTOMapper.apply(savedManufacturer);
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(manufacturerDTOResponse)), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> deleteManufacturer(String code) {
        if (code == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Manufacturer> manufacturer = manufacturerRepository.findByCode(code);
            if (manufacturer.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("MAN-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                manufacturerRepository.delete(manufacturer.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }
}
