package com.reviewcommunity.service;

import com.reviewcommunity.dto.UserDto;
import com.reviewcommunity.entity.Admin;

public interface AdminService {
    Admin registerAdmin(UserDto userDto);

    Admin getAdmin(String email);
}
