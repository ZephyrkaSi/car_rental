package com.gmail.silina.katsiaryna.repository.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDER_STATUS")
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "STATUS")
    private OrderStatusEnum orderStatusEnum;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
}
