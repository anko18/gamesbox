package com.gamesbox.service;

import com.gamesbox.dto.UserDTO;
import com.gamesbox.entity.Role;
import com.gamesbox.entity.User;
import com.gamesbox.repository.RoleRepository;
import com.gamesbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service

public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private Collection<Role> roles;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String userName) {
        // check the database if the user already exists
        return userRepository.findUserByUserName(userName);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public void save(UserDTO user) {
        User newUser = new User();

        // assign user details to the user object
        newUser.setUserName(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setEnabled(true);

        // give user default role of "employee"
        newUser.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_USER")));

        // save user in the database
        userRepository.save(newUser);
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        this.roles = roles;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }


}
