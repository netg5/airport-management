package org.sergei.cargo.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_agents_and_reservations")
public class SalesAgent implements Serializable {

    private static final long serialVersionUID = -5084008929629749866L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sales_agents_and_reservations_id_seq")
    @SequenceGenerator(name = "sales_agents_and_reservations_id_seq",
            sequenceName = "sales_agents_and_reservations_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String agentCode;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "representative")
    private String representative;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

}
