package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByUsername(String username);
}
