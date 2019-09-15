package org.sergei.timetable.service;

import org.sergei.timetable.jpa.model.mappers.TimetableModelListMapper;
import org.sergei.timetable.jpa.repository.TimetableRepository;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.rest.dto.mappers.TimetableDTOListMapper;
import org.sergei.timetable.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ResponseDTO<TimetableDTO>> showTimetable(int page, int size) {
        return null;
    }

    @Override
    public TimetableDTO saveTimetable(TimetableDTO request) {
        return null;
    }
}
