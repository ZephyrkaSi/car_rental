package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Table(name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_UK", columnNames = {"USERNAME", "EMAIL"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements org.springframework.security.core.userdetails.UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 50, message = "Username should be between 2 and 50 characters")
    @Column(name = "USERNAME")
    private String username;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 6, max = 100, message = "Password should be between 6 and 100 characters")
    @Column(name = "PASSWORD")
    private String password;
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "EMAIL")
    private String email;
    @Builder.Default
    @Column(name = "ENABLED")
    private boolean enabled = true;
    @Column(name = "LASTLOGIN")
    private LocalDateTime lastLogin;
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    @JoinColumn(name = "USER_DETAILS_ID", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private UserDetails userDetails;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<RepairInvoice> clientRepairInvoices = new HashSet<>();
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<RepairInvoice> adminRepairInvoices = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName().name());
        return Collections.singleton(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
