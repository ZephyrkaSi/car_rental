package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.repository.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.roleName = :name")
    Role findByName(@Param("name") RoleEnum role);
}
