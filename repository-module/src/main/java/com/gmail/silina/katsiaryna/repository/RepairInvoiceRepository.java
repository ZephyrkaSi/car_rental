package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.RepairInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairInvoiceRepository extends JpaRepository<RepairInvoice, Long> {
}
