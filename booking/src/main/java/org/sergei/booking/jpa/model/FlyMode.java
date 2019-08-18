package org.sergei.booking.jpa.model;

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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fly_modes")
public class FlyMode implements Serializable {
    private static final long serialVersionUID = -2230184548882081865L;

    @Id
    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @JoinTable(name = "fly_modes_prices_relation",
            joinColumns = @JoinColumn(name = "fly_modes_code"),
            inverseJoinColumns = @JoinColumn(name = "prices_code"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Price> price;
}