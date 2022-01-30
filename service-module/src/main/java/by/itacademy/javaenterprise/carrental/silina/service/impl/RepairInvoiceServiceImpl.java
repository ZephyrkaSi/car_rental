package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.RepairInvoiceRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.RepairInvoice;
import by.itacademy.javaenterprise.carrental.silina.service.CarService;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.RepairInvoiceService;
import by.itacademy.javaenterprise.carrental.silina.service.UserService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RepairInvoiceDTO;
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
