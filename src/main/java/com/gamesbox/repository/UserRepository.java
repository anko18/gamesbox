package com.gamesbox.repository;

import com.gamesbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String username);
    User findUserByEmail(String email);
    User save(User user);
}
