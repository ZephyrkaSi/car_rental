package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Table(name = "DISCOUNT_STATUS")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiscountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private DiscountStatusEnum discountStatusEnum;
    @Column(name = "DISCOUNT")
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal discount;
    @OneToMany(mappedBy = "discountStatus", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<UserDetails> userDetailsSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountStatus that = (DiscountStatus) o;

        if (!Objects.equals(id, that.id)) return false;
        if (discountStatusEnum != that.discountStatusEnum) return false;
        return Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (discountStatusEnum != null ? discountStatusEnum.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }
}
