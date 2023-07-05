package com.reviewcommunity.controller;

import com.reviewcommunity.dto.LoginDto;
import com.reviewcommunity.dto.UserDto;
import com.reviewcommunity.entity.Admin;
import com.reviewcommunity.exception.RegistrationException;
import com.reviewcommunity.service.impl.AdminServiceImpl;
import com.reviewcommunity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    @Qualifier("adminAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminServiceImpl adminService;

    /*
    *   Register a new admin by taking
    *   email, first name, last name, and password
    *   as input
    * */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            Admin newAdmin = adminService.registerAdmin(userDto);
        } catch (RegistrationException e) {
            responseBody.put("message", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        responseBody.put("status", "" + HttpStatus.CREATED.value());
        responseBody.put("message", "Admin Registered successfully");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /*
     *   Login an admin and authenticate admin
     *   by taking email and password as input
     *   and on successful authentication
     *   generating a jwt token and returning
     *   it to the client
     * */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            Admin admin = adminService.getAdmin(userDetails.getUsername());


            responseBody.put("email", admin.getEmail());
            responseBody.put("admin_first_name", admin.getFirstName());
            responseBody.put("admin_last_name", admin.getLastName());
            responseBody.put("token", token);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (AuthenticationException authenticationException) {
            responseBody.put("message", "Invalid credentials");
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        responseBody.put("message", "User logout successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
