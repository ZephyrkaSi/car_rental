package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.service.dto.RepairInvoiceDTO;

import java.util.List;

public interface RepairInvoiceService {

    List<RepairInvoiceDTO> getAllRepairInvoiceDTOs();

    void addRepairInvoice(RepairInvoiceDTO repairInvoiceDTO);

}
