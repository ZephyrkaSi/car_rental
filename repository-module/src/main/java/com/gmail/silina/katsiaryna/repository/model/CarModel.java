package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAR_MODEL")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "BRAND_NAME")
    private String brandName;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "BODY_TYPE")
    private String bodyType;
    @Column(name = "FUEL_TYPE")
    private String fuelType;
    @Column(name = "ENGINE_VOLUME")
    private Double engineVolume;
    @Column(name = "TRANSMISSION")
    private String transmission;
    @Column(name = "BODY_COLOR")
    private String bodyColor;
    @Column(name = "INTERIOR_COLOR")
    private String interiorColor;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "CAR_MODEL_ID")
    @ToString.Exclude
    private Set<Car> cars = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarModel carModel = (CarModel) o;

        if (!id.equals(carModel.id)) return false;
        if (!brandName.equals(carModel.brandName)) return false;
        if (!model.equals(carModel.model)) return false;
        if (!bodyType.equals(carModel.bodyType)) return false;
        if (!fuelType.equals(carModel.fuelType)) return false;
        if ((Math.round(engineVolume * 10.0) / 10.0) != (Math.round(carModel.engineVolume * 10.0) / 10.0)) return false;
        if (!transmission.equals(carModel.transmission)) return false;
        if (!bodyColor.equals(carModel.bodyColor)) return false;
        return interiorColor.equals(carModel.interiorColor);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}