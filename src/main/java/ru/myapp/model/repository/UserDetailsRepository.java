package ru.myapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.myapp.model.entity.UserDetails;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    @Query("SELECT UD FROM UserDetails UD WHERE UD.userName = :userName")
    UserDetails getUserDetailsByUserName(@Param("userName") String userName);

    @Query(value = "SELECT UD.EMAIL FROM USER_DETAILS UD JOIN AUTHORITY A ON UD.USERNAME = A.USERNAME WHERE A.AUTHORITY = 'CLIENT'", nativeQuery = true)
    List<String> getClientsEmails();
}
