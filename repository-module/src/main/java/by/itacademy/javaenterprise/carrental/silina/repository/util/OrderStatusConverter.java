package by.itacademy.javaenterprise.carrental.silina.repository.util;

import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatusEnum orderStatusEnum) {
        if (orderStatusEnum == null) {
            return null;
        }
        return orderStatusEnum.getStatus();
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(OrderStatusEnum.values())
                .filter(c -> c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
