package com.gmail.silina.katsiaryna.repository.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Builder
@Table(name = "CAR_STATUS")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STATUS")
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private CarStatusEnum carStatus;
    @OneToMany(mappedBy = "carStatus")
    @ToString.Exclude
    private Collection<Car> cars;
}