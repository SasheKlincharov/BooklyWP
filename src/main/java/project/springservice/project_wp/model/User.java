package project.springservice.project_wp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "korisnik")
public class User implements UserDetails {
    @Id
    private String username;

    private String password;

    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tenant tenantOwner;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    public User() {}

    public User(String username, String password, String firstName, String lastName) {
        this(username,password,firstName,lastName,null);
    }

    public User(String username, String password, String firstName, String lastName, Tenant tenant) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tenantOwner = tenant;
        schedules = new ArrayList<>();
        this.role = Role.ROLE_USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
