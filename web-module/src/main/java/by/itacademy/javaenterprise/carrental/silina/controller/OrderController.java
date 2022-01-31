package by.itacademy.javaenterprise.carrental.silina.controller;

import by.itacademy.javaenterprise.carrental.silina.service.CarModelService;
import by.itacademy.javaenterprise.carrental.silina.service.OrderService;
import by.itacademy.javaenterprise.carrental.silina.service.OrderStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderFormDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.OrderException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.itacademy.javaenterprise.carrental.silina.constant.HandlerConstants.ORDERS_URL;

@Slf4j
@Controller
@RequestMapping(ORDERS_URL)
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

    @GetMapping("/status/form")
    public String addOrderForm(@ModelAttribute("order") OrderDTO orderDTO) {
        return "/update_order_status_form";
    }

    @PostMapping("/status/form")
    public String saveOrder(@ModelAttribute OrderDTO orderDTO) {
        orderService.updateOrderStatusAndDeclineReasonFrom(orderDTO);
        return "redirect:/orders";
    }

    @GetMapping("/statuses/form")
    public String showUpdateForm(@RequestParam Long orderId, Model model) {
        var orderDTO = orderService.getOrderDTOById(orderId);
        model.addAttribute("order", orderDTO);
        model.addAttribute("orderStatuses", orderStatusService.getEligibleStatusesForOrder(orderDTO));
        return "/update_order_status_form";
    }

    @GetMapping("/form")
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
