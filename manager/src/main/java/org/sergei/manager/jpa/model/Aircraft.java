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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

    private static final long serialVersionUID = -155783393887085614L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "aircraft_id_seq")
    @SequenceGenerator(name = "aircraft_id_seq",
            sequenceName = "aircraft_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "aircraft_name")
    private String aircraftName;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "exploitation_period")
    private Integer exploitationPeriod;

    @Column(name = "available")
    private Integer available;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "manufacturer_id",
            referencedColumnName = "id"
    )
    private Manufacturer manufacturer;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "hangar_id",
            referencedColumnName = "id"
    )
    private Hangar hangar;
}
