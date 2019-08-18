package org.sergei.auth.jpa.model;

import lombok.*;

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
@Table(name = "auth_user_roles")
public class AuthUserRoles implements Serializable {

    private static final long serialVersionUID = -8334128934150131617L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "auth_user_role_seq")
    @SequenceGenerator(name = "auth_user_role_seq",
            sequenceName = "auth_user_role_seq", allocationSize = 1)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

}
