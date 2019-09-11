package org.sergei.timetable.service;

import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.rest.dto.response.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class TimetableServiceImpl implements TimetableService {

    @Override
    public List<ResponseDTO<TimetableDTO>> showTimetable(int page, int size) {
        return null;
    }

    @Override
    public TimetableDTO saveTimetable(TimetableDTO request) {
        return null;
    }
}
