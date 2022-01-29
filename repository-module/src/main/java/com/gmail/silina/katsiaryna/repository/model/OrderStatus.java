package com.gmail.silina.katsiaryna.repository.model;

import com.gmail.silina.katsiaryna.repository.util.OrderStatusConverter;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDER_STATUS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STATUS")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatusEnum orderStatus;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatus that = (OrderStatus) o;

        if (!Objects.equals(id, that.id)) return false;
        if (orderStatus != that.orderStatus) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
