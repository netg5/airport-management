package org.sergei.timetable.jpa.model.mappers;

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
public class TimetableModelListMapper implements IMapper<List<TimetableDTO>, List<Timetable>> {

    private final TimetableModelMapper timetableModelMapper;

    @Autowired
    public TimetableModelListMapper(TimetableModelMapper timetableModelMapper) {
        this.timetableModelMapper = timetableModelMapper;
    }

    @Override
    public List<Timetable> apply(List<TimetableDTO> timetableDTOS) {
        return timetableModelMapper.applyList(timetableDTOS);
    }
}
