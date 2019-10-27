package org.sergei.manager.jpa.model;

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
@Table(name = "prices")
public class Price implements Serializable {
    private static final long serialVersionUID = 8935033626830093932L;

    @Id
    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @NotNull
    @ManyToMany(mappedBy = "prices",
            fetch = FetchType.LAZY)
    private List<FlyMode> flyModes;
}
