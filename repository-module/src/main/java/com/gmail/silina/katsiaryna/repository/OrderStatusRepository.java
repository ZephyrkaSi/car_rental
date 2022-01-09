package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.OrderStatus;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    @Query("SELECT os FROM OrderStatus os WHERE os.orderStatusEnum = :status")
    OrderStatus findByStatus(@Param("status") OrderStatusEnum status);
}
