package com.vega.demo.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
public class UserSpringIt implements UserDetails {
    @Id @GeneratedValue
    private long id;
    @NonNull
    @Column(nullable = false, unique = true)
    private String email;
    @NonNull
    private String password;
    @NonNull
    @Column(nullable = false)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable ( name = "users_roles",
                 joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
                 inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
    public void addRole(Role role){
        roles.add(role);
    }
    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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
