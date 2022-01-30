package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
    @Query("SELECT cs FROM CarStatus cs WHERE cs.carStatus = :status")
    CarStatus findByStatus(@Param("status") CarStatusEnum status);
}
