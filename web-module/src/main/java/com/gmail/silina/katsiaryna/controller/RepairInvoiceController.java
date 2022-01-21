package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.repository.model.RoleEnum;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.RepairInvoiceService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.RepairInvoiceDTO;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/invoices")
@AllArgsConstructor
public class RepairInvoiceController {
    private final RepairInvoiceService repairInvoiceService;
    private final UserService userService;
    private final CarService carService;

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("repairInvoices", repairInvoiceService.getAllRepairInvoiceDTOs());
        return "repair_invoices";
    }

    @GetMapping("/form")
    public String addRepairInvoiceForm(@ModelAttribute("repairInvoice") RepairInvoiceDTO repairInvoiceDTO,
                                       Model model) {
        model.addAttribute("cars", carService.getAllCarDTOs());
        model.addAttribute("clients", userService.getAllUserDTOsByRoleName(RoleEnum.CLIENT));
        return "repair_invoice_form";
    }

    @PostMapping("/save")
    public String saveRepairInvoice(@RequestBody @ModelAttribute("repairInvoice") @Valid RepairInvoiceDTO repairInvoiceDTO,
                                    BindingResult resultRepairInvoice) {
        if (resultRepairInvoice.hasErrors()) {
            return "repair_invoice_form";
        } else {
            try {
                repairInvoiceService.addRepairInvoice(repairInvoiceDTO);
            } catch (OrderException e) {
                resultRepairInvoice.addError(new ObjectError("serviceError", e.getMessage()));
                return "repair_invoice_form";
            }
            return "redirect:/invoices";
        }
    }


}
