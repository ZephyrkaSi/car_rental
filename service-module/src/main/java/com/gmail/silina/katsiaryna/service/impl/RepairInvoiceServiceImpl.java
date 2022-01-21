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

import java.util.ArrayList;
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
        List<RepairInvoiceDTO> repairInvoicesDTO = new ArrayList<>();
        var repairInvoices = repairInvoiceRepository.findAll();
        if (repairInvoices.size() == 0) {
            return repairInvoicesDTO;
        } else {
            return convertService.getDTOsFromObjectList(repairInvoices, RepairInvoiceDTO.class);
        }
    }

/*    @Override
    public List<RepairInvoiceDTO> getAllRepairInvoiceDTOs() {
        List<RepairInvoiceDTO> repairInvoicesDTO = new ArrayList<>();
        try {
            var repairInvoices = repairInvoiceRepository.findAll();
            return convertService.getDTOsFromObjectList(repairInvoices, RepairInvoiceDTO.class);
        } catch (RepositoryException e) {
            log.error("Get list of RepairInvoiceDTO exception. {}", e.getMessage());
            return repairInvoicesDTO;
        }
    }*/

    @Override
    public void addRepairInvoice(RepairInvoiceDTO repairInvoiceDTO) {
        var repairInvoice = convertService.getObjectFromDTO(repairInvoiceDTO, RepairInvoice.class);
        var clientId = repairInvoiceDTO.getClient().getId();
        var client = userService.getUserById(clientId);
        repairInvoice.setClient(client);

        var carId = repairInvoiceDTO.getCar().getId();
        var car = carService.getCarById(carId);
        repairInvoice.setCar(car);

        //TODO admin!
        var admin = userService.getUserById(24L);
        repairInvoice.setAdmin(admin);

        repairInvoiceRepository.save(repairInvoice);
    }
}
