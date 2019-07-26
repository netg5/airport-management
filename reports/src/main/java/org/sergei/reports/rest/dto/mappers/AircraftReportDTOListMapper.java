package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.AircraftReport;
import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftReportDTOListMapper implements IMapper<List<AircraftReport>, List<AircraftReportDTO>> {

    private final AircraftReportDTOMapper aircraftReportDTOMapper;

    @Autowired
    public AircraftReportDTOListMapper(AircraftReportDTOMapper aircraftReportDTOMapper) {
        this.aircraftReportDTOMapper = aircraftReportDTOMapper;
    }

    @Override
    public List<AircraftReportDTO> apply(List<AircraftReport> aircraftReports) {
        return aircraftReportDTOMapper.applyList(aircraftReports);
    }
}
