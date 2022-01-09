package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
