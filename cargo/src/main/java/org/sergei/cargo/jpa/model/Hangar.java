package org.sergei.cargo.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "hangar_capacity")
    private Integer capacity;

    @Column(name = "hangar_location")
    private String hangarLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hangar",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = "hangar")
    private List<Aircraft> aircrafts;
}
