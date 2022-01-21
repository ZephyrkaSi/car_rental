package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.service.ConvertService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConvertServiceImpl implements ConvertService {
    private final ModelMapper modelMapper;

    @Override
    public <T, D> T getObjectFromDTO(D dto, Class<T> clazz) {
        return modelMapper.map(dto, clazz);
    }

    @Override
    public <T, D> D getDTOFromObject(T obj, Class<D> dto) {
        return modelMapper.map(obj, dto);
    }

    @Override
    public <T, D> List<D> getDTOsFromObjectList(List<T> list, Class<D> clazz) {
        return list.stream()
                .map(el -> modelMapper.map(el, clazz))
                .collect(Collectors.toList());
    }
}
