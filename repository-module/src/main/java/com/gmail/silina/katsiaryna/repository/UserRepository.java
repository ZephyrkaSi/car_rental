package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.RoleEnum;
import com.gmail.silina.katsiaryna.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String login);

    @Query("SELECT u FROM User u WHERE u.role.roleName = :roleName")
    List<User> findAllByRoleName(RoleEnum roleName);
}
