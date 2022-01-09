package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}
