package org.sergei.auth.jpa.model;

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
public class AuthUserDetails implements UserDetails {

    private static final long serialVersionUID = -751244786587948564L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public AuthUserDetails(AuthUser authUser) {
        this.username = authUser.getUsername();
        this.password = authUser.getPassword();
        this.authorities = translateRoles(authUser.getAuthUserRoles());
    }

    /**
     * Method to set 'ROLE_' prefix for each role for a particular user taken from the database
     *
     * @param authUserRoles Takes a list of user roles from constructor as a parameter
     * @return a collection of authorities
     */
    private Collection<? extends GrantedAuthority> translateRoles(List<AuthUserRoles> authUserRoles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (AuthUserRoles role : authUserRoles) {
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
