package org.sergei.timetable.jpa.model.mappers;

import org.sergei.timetable.jpa.model.Timetable;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class TimetableModelMapper implements IMapper<TimetableDTO, Timetable> {

    @Override
    public Timetable apply(TimetableDTO timetableDTO) {
        return Timetable.builder()
                .departureTime(timetableDTO.getDepartureTime())
                .arrivalTime(timetableDTO.getArrivalTime())
                .destination(timetableDTO.getDestination())
                .flightNumber(timetableDTO.getFlightNumber())
                .hoursFlying(timetableDTO.getHoursFlying())
                .scheduledTime(timetableDTO.getScheduledTime())
                .build();
    }
}
