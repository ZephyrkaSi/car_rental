package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.service.dto.RepairInvoiceDTO;

import java.util.List;

public interface RepairInvoiceService {

    List<RepairInvoiceDTO> getAllRepairInvoiceDTOs();

    void addRepairInvoice(RepairInvoiceDTO repairInvoiceDTO);

}
