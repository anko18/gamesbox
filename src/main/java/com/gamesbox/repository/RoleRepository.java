package com.gamesbox.repository;

import com.gamesbox.entity.Role;
import com.gamesbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);

}
