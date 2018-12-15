package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class AircraftService implements IService<AircraftDTO> {
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private final ModelMapper modelMapper;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(ModelMapper modelMapper, AircraftRepository aircraftRepository) {
        this.modelMapper = modelMapper;
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Find aircraftDTO by ID
     *
     * @param aircraftId gets aircraftDTO ID as parameter
     * @return aircraftDTO DTO
     */
    @Override
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    @Override
    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        return ObjectMapperUtils.mapAll(aircraftList, AircraftDTO.class);
    }

    @Override
    public Page<AircraftDTO> findAllPaginated(int page, int size) {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(page, size));
        return ObjectMapperUtils.mapAllPages(aircraftList, AircraftDTO.class);
    }

    /**
     * Save aircraftDTO
     *
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return Aircraft DTO
     */
    @Override
    public AircraftDTO save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return modelMapper.map(savedAircraft, AircraftDTO.class);
    }

    /**
     * Update aicraft by ID
     *
     * @param aircraftId  get aircraftDTO ID as a parameter
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return aircraftDTO DTO
     */
    @Override
    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        aircraft.setAircraftId(aircraftId);
        aircraft.setAircraftName(aircraftDTO.getAircraftName());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setAircraftWeight(aircraftDTO.getAircraftWeight());
        aircraft.setMaxPassengers(aircraftDTO.getMaxPassengers());

        aircraftRepository.save(aircraft);

        return aircraftDTO;
    }

    /**
     * Method to update one field of the aircraft
     *
     * @param aircraftId ID for aircraft that should be updated
     * @param params     list of params that should be updated
     * @return updated aircraft
     */
    @Override
    public AircraftDTO patch(Long aircraftId, Map<String, Object> params) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        if (params.get("model") != null) {
            aircraft.setModel(String.valueOf(params.get("model")));
        }
        if (params.get("aircraftName") != null) {
            aircraft.setAircraftName(String.valueOf(params.get("aircraftName")));
        }
        if (params.get("aircraftWeight") != null) {
            aircraft.setAircraftWeight(Double.valueOf(String.valueOf(params.get("aircraftWeight"))));
        }
        if (params.get("maxPassengers") != null) {
            aircraft.setMaxPassengers(Integer.valueOf(String.valueOf(params.get("maxPassengers"))));
        }
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    /**
     * Delete aircraftDTO by ID
     *
     * @param aircraftId get aircraftDTO ID as a parameter
     * @return aircraftDTO DTO as a response
     */
    @Override
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        aircraftRepository.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}