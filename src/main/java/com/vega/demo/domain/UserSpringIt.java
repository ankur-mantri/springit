package com.vega.demo.domain;

import com.vega.demo.domain.validator.PasswordsMatch;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@PasswordsMatch
public class UserSpringIt implements UserDetails {
    @Id @GeneratedValue
    private long id;
    @NonNull
    @Size(min = 8, max = 100)
    @Column(nullable = false, unique = true)
    private String email;
    @NonNull
    @Column(length = 100)
    private String password;

    @Transient
    @NotEmpty(message = "Please enter Confirmation Password")
    private String confirmPassword;

    @NonNull
    @Column(nullable = false)
    private Boolean enabled;

    @NonNull
    @NotEmpty(message = "Enter first Name")
    private String firstName;
    @NonNull
    @NotEmpty(message = "You must enter Last Name.")
    @NotBlank(message = "Please enter Last NAme")
    private String lastName;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    @NonNull
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Please enter alias.")
    @NotBlank(message = "Please enter alias.")
    private String alias;

    private String activationCode;
    public String getFullName(){
        return firstName + " "+ lastName;
    }
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
