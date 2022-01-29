package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.UserDetailsRepository;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatusEnum;
import com.gmail.silina.katsiaryna.repository.model.UserDetails;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.DiscountStatusService;
import com.gmail.silina.katsiaryna.service.UserDetailsService;
import com.gmail.silina.katsiaryna.service.dto.UserDetailsDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final DiscountStatusService discountStatusService;
    private final ConvertService convertService;

    @Override
    public UserDetails getUserDetailsById(Long id) {
        if (id == null) {
            log.error("User details id cannot be null");
            throw new ServiceException("User details id cannot be null");
        } else {
            var optionalUserDetails = userDetailsRepository.findById(id);
            if (optionalUserDetails.isPresent()) {
                return optionalUserDetails.get();
            } else {
                log.error("User details with id {} doesn't exist", id);
                throw new ServiceException("User details with id " + id + " doesn't exist");
            }
        }
    }

    @Override
    public UserDetailsDTO getUserDetailsDTOById(Long id) {
        if (id == null) {
            log.error("User details id cannot be null");
            throw new ServiceException("User details id cannot be null");
        } else {
            var userDetails = getUserDetailsById(id);
            if (userDetails == null) {
                log.error("User details with id {} doesn't exist", id);
                throw new ServiceException("User details with id " + id + " doesn't exist");
            } else {
                return convertService.getDTOFromObject(userDetails, UserDetailsDTO.class);
            }
        }
    }

    @Override
    public void add(UserDetails userDetails) {
        var discountStatus = discountStatusService.getDiscountStatus(DiscountStatusEnum.BRONZE);
        userDetails.setDiscountStatus(discountStatus);
        log.info("Saving user details.");
        userDetailsRepository.save(userDetails);
    }

    @Override
    public void changeUserDetailsFrom(UserDetailsDTO userDetailsDTO) {
        var userDetailsId = userDetailsDTO.getId();
        var userDetails = getUserDetailsById(userDetailsId);

        var firstName = userDetailsDTO.getFirstName();
        var lastName = userDetailsDTO.getLastName();
        var passportData = userDetailsDTO.getPassportData();

        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setPassportData(passportData);
        log.info("Changing user details.");
        userDetailsRepository.save(userDetails);
    }
}
