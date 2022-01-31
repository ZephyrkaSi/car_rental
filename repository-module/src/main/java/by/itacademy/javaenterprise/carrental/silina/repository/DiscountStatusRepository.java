package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountStatusRepository extends JpaRepository<DiscountStatus, Integer> {
    @Query("SELECT ds FROM DiscountStatus ds WHERE ds.discountStatus = :status")
    DiscountStatus findByStatus(@Param("status") DiscountStatusEnum status);
}
