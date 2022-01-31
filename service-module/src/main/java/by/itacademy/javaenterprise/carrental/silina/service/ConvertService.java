package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.service.dto.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConvertService {

    <T, D> T getObjectFromDTO(D dto, Class<T> clazz);

    <T, D> D getDTOFromObject(T obj, Class<D> dto);

    <T, D> List<D> getDTOsFromObjectList(List<T> list, Class<D> clazz);

    <T, D> PageDTO<D> getPageDTOFromPage(Page<T> page, Class<D> clazz);
}
