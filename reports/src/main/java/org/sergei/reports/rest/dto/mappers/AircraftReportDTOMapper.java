package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.AircraftReport;
import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftReportDTOMapper implements IMapper<AircraftReport, AircraftReportDTO> {
    @Override
    public AircraftReportDTO apply(AircraftReport aircraftReport) {
        AircraftReportDTO aircraftReportDTO = new AircraftReportDTO();

        aircraftReportDTO.setAircraftId(aircraftReport.getAircraftId());
        aircraftReportDTO.setAircraftName(aircraftReport.getAircraftName());
        aircraftReportDTO.setModel(aircraftReport.getModel());
        aircraftReportDTO.setDistance(aircraftReport.getDistance());
        aircraftReportDTO.setPlace(aircraftReport.getPlace());
        aircraftReportDTO.setPrice(aircraftReport.getPrice());
        aircraftReportDTO.setRouteId(aircraftReport.getRouteId());

        return aircraftReportDTO;
    }
}
