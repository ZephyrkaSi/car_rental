package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@Table(name = "USER_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    /*    @GenericGenerator(
                name = "generator",
                strategy = "foreign",
                parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user")
        )*/
    //@GeneratedValue(generator = "generator")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name should not be empty")
    @Column(name = "FIRST_NAME")
    private String firstName;
    @NotBlank(message = "Last name should not be empty")
    @Column(name = "LAST_NAME")
    private String lastName;
    @NotBlank(message = "Passport data should not be empty")
    @Column(name = "PASSPORT_DATA")
    private String passportData;
    @Column(name = "TOTAL_RENTAL_TIME")
    private Integer totalRentalTime = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCOUNT_STATUS_ID")
    private DiscountStatus discountStatus;
    @OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDetails that = (UserDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
