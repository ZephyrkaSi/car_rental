package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.DiscountStatus;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountStatusRepository extends JpaRepository<DiscountStatus, Integer> {
    @Query("SELECT ds FROM DiscountStatus ds WHERE ds.discountStatus = :status")
    DiscountStatus findByStatus(@Param("status") DiscountStatusEnum status);
}
