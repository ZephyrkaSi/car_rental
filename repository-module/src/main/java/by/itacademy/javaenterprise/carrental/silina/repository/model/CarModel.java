package by.itacademy.javaenterprise.carrental.silina.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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
    private Long id;
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
    @Column(name = "PRICE_PER_MINUTE")
    private Integer pricePerMinute;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "CAR_MODEL_ID")
    @ToString.Exclude
    private Set<Car> cars = new HashSet<>();
    @OneToMany(mappedBy = "carModel", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarModel carModel = (CarModel) o;

        if (!Objects.equals(id, carModel.id)) return false;
        if (!Objects.equals(brandName, carModel.brandName)) return false;
        if (!Objects.equals(model, carModel.model)) return false;
        if (!Objects.equals(bodyType, carModel.bodyType)) return false;
        if (!Objects.equals(fuelType, carModel.fuelType)) return false;
        if (!Objects.equals(engineVolume, carModel.engineVolume))
            return false;
        if (!Objects.equals(transmission, carModel.transmission))
            return false;
        if (!Objects.equals(bodyColor, carModel.bodyColor)) return false;
        if (!Objects.equals(interiorColor, carModel.interiorColor))
            return false;
        return Objects.equals(pricePerMinute, carModel.pricePerMinute);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (bodyType != null ? bodyType.hashCode() : 0);
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (engineVolume != null ? engineVolume.hashCode() : 0);
        result = 31 * result + (transmission != null ? transmission.hashCode() : 0);
        result = 31 * result + (bodyColor != null ? bodyColor.hashCode() : 0);
        result = 31 * result + (interiorColor != null ? interiorColor.hashCode() : 0);
        result = 31 * result + (pricePerMinute != null ? pricePerMinute.hashCode() : 0);
        return result;
    }
}