package org.sergei.auth.jpa.model;

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
@Table(name = "auth_user")
public class AuthUser implements Serializable {

    private static final long serialVersionUID = -8573885834567480787L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "auth_user_seq")
    @SequenceGenerator(name = "auth_user_seq",
            sequenceName = "auth_user_seq", allocationSize = 1)
    private Long id;

    private String username;

    private String password;

    @Column(name = "roles")
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<AuthUserRoles> authUserRoles;
}
