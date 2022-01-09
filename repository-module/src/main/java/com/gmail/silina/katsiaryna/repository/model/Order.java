package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "`ORDER`")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ORDER_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @ToString.Exclude
    private User user;
    @Column(name = "DATE_FROM")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeFrom;
    @Column(name = "DATE_TO")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_MODEL_ID")
    @ToString.Exclude
    private CarModel carModel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID")
    @ToString.Exclude
    private Car car;
    @Column(name = "PRICE_WITH_DISCOUNT")
    private BigDecimal priceWithDiscount;
    @Column(name = "DISCOUNT")
    private BigDecimal discount;
    @Column(name = "DECLINE_REASON")
    private String declineReason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_STATUS_ID")
    @ToString.Exclude
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(orderDate, order.orderDate)) return false;
        if (!Objects.equals(user, order.user)) return false;
        if (!Objects.equals(dateAndTimeFrom, order.dateAndTimeFrom))
            return false;
        if (!Objects.equals(dateAndTimeTo, order.dateAndTimeTo))
            return false;
        if (!Objects.equals(carModel, order.carModel)) return false;
        if (!Objects.equals(car, order.car)) return false;
        if (!Objects.equals(priceWithDiscount, order.priceWithDiscount))
            return false;
        if (!Objects.equals(discount, order.discount)) return false;
        if (!Objects.equals(declineReason, order.declineReason))
            return false;
        return Objects.equals(orderStatus, order.orderStatus);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateAndTimeFrom != null ? dateAndTimeFrom.hashCode() : 0);
        result = 31 * result + (dateAndTimeTo != null ? dateAndTimeTo.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (priceWithDiscount != null ? priceWithDiscount.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (declineReason != null ? declineReason.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
