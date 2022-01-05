package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_UK", columnNames = {"USERNAME", "EMAIL"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
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
    private Date lastLogin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    @JoinColumn(name = "USER_DETAILS_ID", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private UserDetails userDetails;
}
