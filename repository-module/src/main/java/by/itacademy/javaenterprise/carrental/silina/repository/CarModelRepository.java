package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}
