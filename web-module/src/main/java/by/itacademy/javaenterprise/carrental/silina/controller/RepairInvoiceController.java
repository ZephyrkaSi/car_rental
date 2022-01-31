package by.itacademy.javaenterprise.carrental.silina.controller;

import by.itacademy.javaenterprise.carrental.silina.repository.model.RoleEnum;
import by.itacademy.javaenterprise.carrental.silina.service.CarService;
import by.itacademy.javaenterprise.carrental.silina.service.RepairInvoiceService;
import by.itacademy.javaenterprise.carrental.silina.service.UserService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RepairInvoiceDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.OrderException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.itacademy.javaenterprise.carrental.silina.constant.HandlerConstants.INVOICES_URL;

@Controller
@RequestMapping(INVOICES_URL)
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

    @PostMapping("/form")
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
