package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long>, UserDetailsService {
    Optional<User> getByEmail(String email);

    UserDetails loadUserByUsername(String email);
}
