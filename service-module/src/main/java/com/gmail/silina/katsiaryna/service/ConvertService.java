package com.gmail.silina.katsiaryna.service;

import java.util.List;

public interface ConvertService {

    <T, D> T getObjectFromDTO(D dto, Class<T> clazz);

    <T, D> D getDTOFromObject(T obj, Class<D> dto);

    <T, D> List<D> getDTOsFromObjectList(List<T> list, Class<D> clazz);
}
