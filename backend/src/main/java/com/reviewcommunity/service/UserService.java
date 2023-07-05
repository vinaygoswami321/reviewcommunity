package com.reviewcommunity.service;

import com.reviewcommunity.dto.UserDto;
import com.reviewcommunity.entity.User;
import com.reviewcommunity.exception.RegistrationException;

public interface UserService {
    User registerUser(UserDto userDto) throws RegistrationException;

    int getUserCount();

    User getUser(String email);
}
