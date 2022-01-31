package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.Car;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static by.itacademy.javaenterprise.carrental.silina.repository.constant.SqlConstant.CAR_STATUS_NOR_AVAILABLE_FOR_ORDER;
import static by.itacademy.javaenterprise.carrental.silina.repository.constant.SqlConstant.ORDER_DECLINED_STATUSES;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.carStatus = :status")
    List<Car> getCarsByStatus(@Param("status") CarStatus carStatus);

    @Query(value = "SELECT * FROM CAR C " +
            "WHERE C.CAR_MODEL_ID = :modelId " +
            "  AND C.CAR_STATUS_ID NOT IN (SELECT CS.ID FROM CAR_STATUS CS WHERE CS.STATUS IN (" + CAR_STATUS_NOR_AVAILABLE_FOR_ORDER + "))" +
            "  AND NOT EXISTS (SELECT NULL FROM `ORDER` O " +
            //отсекаем ордеры завершенные до текущ момента (до даты-времени оформления нашего заказа)
            "                  WHERE SYSDATE() < O.DATE_TO " +
            "                    AND C.ID = O.CAR_ID " +
            "                    AND O.ORDER_STATUS_ID NOT IN (SELECT OS.ID " +
            "                                                  FROM ORDER_STATUS OS " +
            "                                                  WHERE OS.STATUS IN (" + ORDER_DECLINED_STATUSES + ")) " +
            "                    AND (" +
            "                        (:end BETWEEN DATE_FROM AND DATE_TO)" +
            "                        OR" +
            "                        (DATE_FROM BETWEEN :begin AND :end)" +
            "                        OR" +
            "                        (DATE_TO BETWEEN :begin AND :end)" +
            "                    )" +
            "  )"
            , nativeQuery = true)
    List<Car> findAvailableCars(@Param("modelId") Long modelId,
                                @Param("begin") LocalDateTime begin,
                                @Param("end") LocalDateTime end);
}
