package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o where o.user.id = :userId")
    List<Order> getAllUserOrdersByUserId(Long userId);
}
