package org.sergei.timetable.service;

import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.rest.dto.response.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public interface TimetableService {

    ResponseDTO<TimetableDTO> showTimetable(int page, int size);

    ResponseDTO<TimetableDTO> saveTimetable(TimetableDTO request);
}
