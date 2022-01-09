package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.service.CarModelService;
import com.gmail.silina.katsiaryna.service.OrderService;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import com.gmail.silina.katsiaryna.service.model.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CarModelService carModelService;

    @GetMapping("/add")
    public String redirectToOrderForm(@ModelAttribute("order") OrderDTO orderDTO,
                                      Model model) {
        model.addAttribute("carModels", carModelService.getAll());
        return "order_form";
    }

    @PostMapping("/form")
    public String addOrder(@RequestBody @ModelAttribute("order") @Valid OrderDTO orderDTO,
                           BindingResult resultOrder) {
        if (resultOrder.hasErrors()) {
            return "order_form";
        } else {
            try {
                orderService.addOrder(orderDTO);
            } catch (OrderException e) {
                resultOrder.addError(new ObjectError("serviceError", e.getMessage()));
                return "order_form";
            }
            return "order_success_addition";
        }
    }
}
