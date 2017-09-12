package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jianle on 17-9-12.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

}
