package org.sergei.manager.jpa.model;

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
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport")
public class Airport implements Serializable {

    private static final long serialVersionUID = -6669537595196819645L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "airport_id_seq")
    @SequenceGenerator(name = "airport_id_seq",
            sequenceName = "airport_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "address")
    private String airportAddress;

    @Column(name = "country")
    private String country;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_job")
    private String contactJob;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
