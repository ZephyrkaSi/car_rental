package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gmail.silina.katsiaryna.constant.HandlerConstants.CARS_URL;

@Controller
@RequestMapping(CARS_URL)
@AllArgsConstructor
public class CarController {
    private final CarService carService;
    private final CarStatusService carStatusService;

    @GetMapping
    public String getAllCars(Model model) {
        model.addAttribute("cars", carService.getAllCarDTOs());
        return "cars";
    }

    @GetMapping("/changeCarStatusForm")
    public String updateCarStatusForm(@RequestParam Long carId, Model model) {
        var carDTO = carService.getCarDTOById(carId);
        model.addAttribute("car", carDTO);
        model.addAttribute("carStatuses", carStatusService.getAllCarStatusDTOs());
        return "update_car_status_form";
    }

    @PostMapping("/changeCarStatus")
    public String saveChangedCarStatus(@ModelAttribute @Valid CarDTO carDTO) {
        carService.updateCarStatusFrom(carDTO);
        return "redirect:/cars";
    }
}
