package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    @Query("SELECT os FROM OrderStatus os WHERE os.orderStatus = :status")
    OrderStatus findByStatus(@Param("status") OrderStatusEnum status);
}
