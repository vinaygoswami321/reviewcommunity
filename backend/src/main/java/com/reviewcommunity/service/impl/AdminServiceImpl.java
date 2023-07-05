package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.UserDto;
import com.reviewcommunity.entity.Admin;
import com.reviewcommunity.exception.RegistrationException;
import com.reviewcommunity.repository.AdminRepository;
import com.reviewcommunity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    *  Service to register an admin
    *  and save its credentials in the admin table
    *  after encrypting password using the bcrypt password encoder
    * */
    @Override
    public Admin registerAdmin(UserDto userDto) throws RegistrationException {
        Admin existingUser = adminRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) throw new RegistrationException("User Already registered");

        Admin newAdmin = new Admin();
        try {

            newAdmin.setEmail(userDto.getEmail());
            newAdmin.setFirstName(userDto.getFirstName());
            newAdmin.setLastName(userDto.getLastName());
            newAdmin.setPassword(passwordEncoder.encode(userDto.getPassword()));
            adminRepository.save(newAdmin);

        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("User not registered");
        }

        return newAdmin;

    }

    /*
    *   Service to retrieve an admin by its registered email
    * */
    @Override
    public Admin getAdmin(String email){
        return adminRepository.findByEmail(email);
    }
}
