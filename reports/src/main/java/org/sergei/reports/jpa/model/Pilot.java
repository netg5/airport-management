package org.sergei.reports.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pilot")
public class Pilot implements Serializable {

    private static final long serialVersionUID = 6495063085987376539L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "pilot_id_seq")
    @SequenceGenerator(name = "pilot_id_seq",
            sequenceName = "pilot_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "ssn")
    private String ssn;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "available")
    private Integer available;
}
