package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.CustomerReport;
import org.sergei.reports.rest.dto.CustomerReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class CustomerReportDTOMapper implements IMapper<CustomerReport, CustomerReportDTO> {

    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public CustomerReportDTOMapper(ReservationDTOListMapper reservationDTOListMapper) {
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Override
    public CustomerReportDTO apply(CustomerReport customer) {

        CustomerReportDTO customerReportDTO = new CustomerReportDTO();
        customerReportDTO.setCustomerId(customer.getCustomerId());
        customerReportDTO.setFirstName(customer.getFirstName());
        customerReportDTO.setLastName(customer.getLastName());
        customerReportDTO.setReservations(List.of());

        return customerReportDTO;
    }
}
