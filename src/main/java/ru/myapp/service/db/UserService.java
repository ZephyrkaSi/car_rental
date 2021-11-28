package ru.myapp.service.db;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.myapp.model.entity.UserDetails;
import ru.myapp.model.repository.UserDetailsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserDetailsRepository userDetailsRepository;

    public List<String> getClientsEmails() {
        return userDetailsRepository.getClientsEmails();
    }

    public UserDetails getUserDetailsByUserName(String userName) {
        return userDetailsRepository.getUserDetailsByUserName(userName);
    }
}
