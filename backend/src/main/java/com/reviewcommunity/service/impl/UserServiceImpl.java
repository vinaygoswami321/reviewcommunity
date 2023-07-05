package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.UserDto;
import com.reviewcommunity.entity.User;
import com.reviewcommunity.exception.RegistrationException;
import com.reviewcommunity.repository.UserRepository;
import com.reviewcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    *   Service to register a new user
    *   and save its credentials to the user table
    *   by encrypting the password using the bcrypt password encoder
    * */
    @Override
    public User registerUser(UserDto userDto) throws RegistrationException {
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) throw new RegistrationException("User Already registered");

        User newUser = new User();
        try {
            newUser.setEmail(userDto.getEmail());
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("User not registered");
        }

        return newUser;
    }

    /*
    *   Service to get number of registered users
    * */
    @Override
    public int getUserCount(){
        return userRepository.getUserCount();
    }

    /*
    *   Service to get a user by its email
    * */
    @Override
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }
}
