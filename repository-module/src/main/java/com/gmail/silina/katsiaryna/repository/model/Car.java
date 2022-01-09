package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Table(name = "CAR")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STATE_NUMBER")
    private String stateNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_MODEL_ID")
    @ToString.Exclude
    private CarModel carModel;
    @JoinColumn(name = "CAR_STATUS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private CarStatus carStatus;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<RepairInvoice> repairInvoices = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (!Objects.equals(id, car.id)) return false;
        if (!stateNumber.equals(car.stateNumber)) return false;
        if (!carModel.equals(car.carModel)) return false;
        return carStatus.equals(car.carStatus);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}