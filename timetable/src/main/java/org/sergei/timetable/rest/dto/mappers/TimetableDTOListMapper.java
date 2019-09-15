package org.sergei.timetable.rest.dto.mappers;

import org.sergei.timetable.jpa.model.Timetable;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class TimetableDTOListMapper implements IMapper<List<Timetable>, List<TimetableDTO>> {

    private final TimetableDTOMapper timetableDTOMapper;

    @Autowired
    public TimetableDTOListMapper(TimetableDTOMapper timetableDTOMapper) {
        this.timetableDTOMapper = timetableDTOMapper;
    }

    @Override
    public List<TimetableDTO> apply(List<Timetable> timetables) {
        return timetableDTOMapper.applyList(timetables);
    }
}
