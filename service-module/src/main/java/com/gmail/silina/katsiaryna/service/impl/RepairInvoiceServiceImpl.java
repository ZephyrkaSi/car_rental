package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.RepairInvoiceRepository;
import com.gmail.silina.katsiaryna.repository.model.RepairInvoice;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.RepairInvoiceService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.RepairInvoiceDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RepairInvoiceServiceImpl implements RepairInvoiceService {
    private final RepairInvoiceRepository repairInvoiceRepository;
    private final ConvertService convertService;
    private final UserService userService;
    private final CarService carService;

    @Override
    public List<RepairInvoiceDTO> getAllRepairInvoiceDTOs() {
        var repairInvoices = repairInvoiceRepository.findAll();
        return convertService.getDTOsFromObjectList(repairInvoices, RepairInvoiceDTO.class);
    }

    @Override
    public void addRepairInvoice(RepairInvoiceDTO repairInvoiceDTO) {
        var repairInvoice = convertService.getObjectFromDTO(repairInvoiceDTO, RepairInvoice.class);
        var clientId = repairInvoiceDTO.getClient().getId();
        var client = userService.getUserById(clientId);
        repairInvoice.setClient(client);

        var carId = repairInvoiceDTO.getCar().getId();
        var car = carService.getCarById(carId);
        repairInvoice.setCar(car);

        var adminId = userService.getPrincipalUserId();
        var admin = userService.getUserById(adminId);
        repairInvoice.setAdmin(admin);

        repairInvoiceRepository.save(repairInvoice);
    }
}
