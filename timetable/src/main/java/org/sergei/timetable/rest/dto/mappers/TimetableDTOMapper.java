package org.sergei.timetable.rest.dto.mappers;

import org.sergei.timetable.jpa.model.Timetable;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class TimetableDTOMapper implements IMapper<Timetable, TimetableDTO> {

    @Override
    public TimetableDTO apply(Timetable timetable) {
        return TimetableDTO.builder()
                .departureTime(timetable.getDepartureTime())
                .arrivalTime(timetable.getArrivalTime())
                .destination(timetable.getDestination())
                .flightNumber(timetable.getFlightNumber())
                .hoursFlying(timetable.getHoursFlying())
                .scheduledTime(timetable.getScheduledTime())
                .build();
    }
}
