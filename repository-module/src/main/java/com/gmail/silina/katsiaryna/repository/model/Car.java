package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    private Integer id;
    @Column(name = "STATE_NUMBER")
    private String stateNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_MODEL_ID")
    private CarModel carModel;
    @JoinColumn(name = "CAR_STATUS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CarStatus carStatus;

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