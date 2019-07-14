/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.auth.jpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
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

    public AuthUserRoles(String roleName) {
        this.roleName = roleName;
    }
}
