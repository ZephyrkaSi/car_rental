package by.itacademy.javaenterprise.carrental.silina.controller;

import by.itacademy.javaenterprise.carrental.silina.service.CarService;
import by.itacademy.javaenterprise.carrental.silina.service.CarStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.itacademy.javaenterprise.carrental.silina.constant.HandlerConstants.CARS_URL;

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
