package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.service.CarModelService;
import com.gmail.silina.katsiaryna.service.OrderService;
import com.gmail.silina.katsiaryna.service.OrderStatusService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CarModelService carModelService;
    private final OrderStatusService orderStatusService;

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrderDTOs());
        return "orders";
    }

    @GetMapping("/addOrderForm")
    public String addOrderForm(@ModelAttribute("order") OrderDTO orderDTO) {
        return "update_order_form";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@ModelAttribute OrderDTO orderDTO) {
        orderService.updateOrderStatusAndDeclineReasonFrom(orderDTO);
        return "redirect:/orders";
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam Long orderId, Model model) {
        var orderDTO = orderService.getOrderDTOById(orderId);
        model.addAttribute("order", orderDTO);
        model.addAttribute("orderStatuses", orderStatusService.getEligibleStatusesForOrder(orderDTO));
        return "update_order_form";
    }

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
