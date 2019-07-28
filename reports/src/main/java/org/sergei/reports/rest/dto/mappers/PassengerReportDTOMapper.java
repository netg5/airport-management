package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.CustomerReport;
import org.sergei.reports.rest.dto.PassengerReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerReportDTOMapper implements IMapper<CustomerReport, PassengerReportDTO> {

    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public PassengerReportDTOMapper(ReservationDTOListMapper reservationDTOListMapper) {
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Override
    public PassengerReportDTO apply(CustomerReport customer) {

        PassengerReportDTO passengerReportDTO = new PassengerReportDTO();
        passengerReportDTO.setPassengerId(customer.getCustomerId());
        passengerReportDTO.setFirstName(customer.getFirstName());
        passengerReportDTO.setLastName(customer.getLastName());
        passengerReportDTO.setReservations(List.of());

        return passengerReportDTO;
    }
}
