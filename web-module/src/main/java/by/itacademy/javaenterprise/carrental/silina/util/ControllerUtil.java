package by.itacademy.javaenterprise.carrental.silina.util;

import by.itacademy.javaenterprise.carrental.silina.repository.model.RoleEnum;
import by.itacademy.javaenterprise.carrental.silina.service.dto.UserLogin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllerUtil {

    public static UserLogin getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserLogin) auth.getPrincipal();
    }

    public static boolean isAuthorizedAsClientUser() {
        UserLogin user = getAuthorizedUser();
        return user.getRoleName().equals(RoleEnum.CLIENT.name());
    }
}
