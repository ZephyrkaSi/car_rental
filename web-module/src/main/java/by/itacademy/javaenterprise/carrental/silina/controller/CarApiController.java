package by.itacademy.javaenterprise.carrental.silina.controller;

import by.itacademy.javaenterprise.carrental.silina.service.CarService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.PageDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static by.itacademy.javaenterprise.carrental.silina.constant.HandlerConstants.API_URL;
import static by.itacademy.javaenterprise.carrental.silina.constant.HandlerConstants.CARS_URL;
import static by.itacademy.javaenterprise.carrental.silina.constant.ServiceConstants.DEFAULT_PAGE_SIZE;
import static by.itacademy.javaenterprise.carrental.silina.constant.ServiceConstants.STATE_NUMBER_FIELD;

@RestController
@RequestMapping(API_URL + CARS_URL)
@AllArgsConstructor
public class CarApiController {
    private final CarService carService;

    @GetMapping
    public PageDTO<CarDTO> getAllCars(@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
        return carService.getAllCarDTOsByPage(pageable);
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable @NotNull Long id) {
        return carService.getCarDTOById(id);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable @NotNull Long id, @RequestBody ObjectNode json) {
        if (json.has(STATE_NUMBER_FIELD)) {
            String stateNumber = json.get(STATE_NUMBER_FIELD).asText();
            return carService.updateStateNumber(id, stateNumber);
        } else {
            throw new ServiceException("JSON object has no field " + STATE_NUMBER_FIELD);
        }
    }
}
