package org.sergei.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
@Immutable
@Entity
@Table(name = "customer")
public class BaseCustomer {

    @Id
    private Long customerId;
}
