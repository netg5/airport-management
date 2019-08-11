package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.PassengerReport;
import org.sergei.reports.rest.dto.PassengerReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerReportDTOMapper implements IMapper<PassengerReport, PassengerReportDTO> {

    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public PassengerReportDTOMapper(ReservationDTOListMapper reservationDTOListMapper) {
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Override
    public PassengerReportDTO apply(PassengerReport customer) {

        PassengerReportDTO passengerReportDTO = new PassengerReportDTO();
        passengerReportDTO.setPassengerId(customer.getPassengerId());
        passengerReportDTO.setFirstName(customer.getFirstName());
        passengerReportDTO.setLastName(customer.getLastName());
        passengerReportDTO.setReservations(List.of());

        return passengerReportDTO;
    }
}
