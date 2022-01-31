package by.itacademy.javaenterprise.carrental.silina.repository.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlConstant {
    public static final String ORDER_DECLINED_STATUSES = "'DECLINED_BY_ADMIN', 'CANCELED_BY_CLIENT'";
    public static final String CAR_STATUS_NOR_AVAILABLE_FOR_ORDER = "'BROKEN', 'REPAIRING'";
}
