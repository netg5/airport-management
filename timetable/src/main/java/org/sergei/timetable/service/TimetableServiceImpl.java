package org.sergei.timetable.service;

import org.sergei.timetable.jpa.model.Timetable;
import org.sergei.timetable.jpa.model.mappers.TimetableModelListMapper;
import org.sergei.timetable.jpa.repository.TimetableRepository;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.rest.dto.mappers.TimetableDTOListMapper;
import org.sergei.timetable.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final TimetableDTOListMapper timetableDTOListMapper;
    private final TimetableModelListMapper timetableModelListMapper;

    @Autowired
    public TimetableServiceImpl(TimetableRepository timetableRepository,
                                TimetableDTOListMapper timetableDTOListMapper,
                                TimetableModelListMapper timetableModelListMapper) {
        this.timetableRepository = timetableRepository;
        this.timetableDTOListMapper = timetableDTOListMapper;
        this.timetableModelListMapper = timetableModelListMapper;
    }

    @Override
    public ResponseDTO<TimetableDTO> showTimetable(int page, int size) {
        Page<Timetable> timetableList = timetableRepository.findAll(PageRequest.of(page, size));
        List<TimetableDTO> timetableDTOList = timetableDTOListMapper.apply(timetableList.getContent());

        ResponseDTO<TimetableDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(timetableDTOList);

        return response;
    }

    @Override
    public ResponseDTO<TimetableDTO> saveTimetable(TimetableDTO request) {
        return null;
    }
}
