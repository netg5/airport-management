package org.sergei.manager.jpa.model;

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
@Entity
@Table(name = "hangar")
public class Hangar implements Serializable {

    private static final long serialVersionUID = 3403853570444000375L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "hangar_id_seq")
    @SequenceGenerator(name = "hangar_id_seq",
            sequenceName = "hangar_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "hangar_number")
    private String hangarNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "hangar_location")
    private String hangarLocation;
}
