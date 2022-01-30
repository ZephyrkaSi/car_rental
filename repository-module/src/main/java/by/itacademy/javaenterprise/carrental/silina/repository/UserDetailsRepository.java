package by.itacademy.javaenterprise.carrental.silina.repository;

import by.itacademy.javaenterprise.carrental.silina.repository.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
