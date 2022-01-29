package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import com.gmail.silina.katsiaryna.service.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.silina.katsiaryna.constant.HandlerConstants.API_URL;
import static com.gmail.silina.katsiaryna.constant.HandlerConstants.CARS_URL;

@RestController
@RequestMapping(API_URL + CARS_URL)
@AllArgsConstructor
public class CarApiController {
    private final CarService carService;

    @GetMapping
    public PageDTO<CarDTO> getAllCars(@PageableDefault(size = 3) Pageable pageable) {
        return carService.getAllCarDTOsByPage(pageable);
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable Long id) {
        return carService.getCarDTOById(id);
    }
}
