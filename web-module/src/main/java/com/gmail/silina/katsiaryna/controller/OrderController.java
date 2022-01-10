package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.service.CarModelService;
import com.gmail.silina.katsiaryna.service.OrderService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CarModelService carModelService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/all")
    @ResponseBody
    public List<OrderDTO> getAllOrdersApi() {
        return orderService.getAllOrders();
    }

    @GetMapping("/all")
    public String getAllOrders() {
        return "orders";
    }

    @GetMapping("/angular")
    @ResponseBody
    public Iterable<OrderDTO> angular() {
        return orderService.getAllOrders();
    }

/*    @RequestMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }*/


    @GetMapping("/add")
    public String redirectToOrderForm(@ModelAttribute("order") OrderFormDTO orderFormDTO,
                                      Model model) {
        model.addAttribute("carModels", carModelService.getAll());
        return "order_form";
    }

    @PostMapping("/form")
    public String addOrder(@RequestBody @ModelAttribute("order") @Valid OrderFormDTO orderFormDTO,
                           BindingResult resultOrder) {
        if (resultOrder.hasErrors()) {
            return "order_form";
        } else {
            try {
                orderService.addOrder(orderFormDTO);
            } catch (OrderException e) {
                resultOrder.addError(new ObjectError("serviceError", e.getMessage()));
                return "order_form";
            }
            return "order_success_addition";
        }
    }
}
