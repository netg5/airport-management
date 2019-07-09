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

package org.sergei.authservice.jpa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class creates a set of GrantedAuthority instances that represent roles that the user has in the system.
 *
 * @author Sergei Visotsky
 */
public class ApiUserDetails implements UserDetails {

    private static final long serialVersionUID = -751244786587948564L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public ApiUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translateRoles(user.getUserRoles());
    }

    /**
     * Method to set 'ROLE_' prefix for each role for a particular user taken from the database
     *
     * @param userRoles Takes a list of user roles from constructor as a parameter
     * @return a collection of authorities
     */
    private Collection<? extends GrantedAuthority> translateRoles(List<UserRoles> userRoles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRoles role : userRoles) {
            String name = role.getRoleName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }
        return grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
