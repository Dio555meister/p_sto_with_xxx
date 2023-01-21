package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A DTO for the {@link User} entity
 */
@Repository
public interface UserDtoDao {
    Optional<UserDto> getUserById(Long id);
}