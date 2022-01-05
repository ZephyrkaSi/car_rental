package com.gmail.silina.katsiaryna.repository;

import com.gmail.silina.katsiaryna.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>/*GenericRepository<Long, User>*/ {
  /*  User getUserByEmail(String username);

    List<User> findAll(int pageNumber, int pageSize);*/
}
