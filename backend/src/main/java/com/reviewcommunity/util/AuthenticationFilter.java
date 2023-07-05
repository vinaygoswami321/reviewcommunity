package com.reviewcommunity.util;

import com.reviewcommunity.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /*
    *  Filter the incoming request to authenticate the unauthorised requests
    *  by using the jwt util
    * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String authorizationHeader = request.getHeader("Authorization"); //extracting jwt token from the http header of the request
         String token = null;
         String username = null;

         if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
             token = authorizationHeader.substring(7);
             username = jwtUtil.extractUserName(token);
         }

         try {
             //check if the current request is already authenticated or not
             if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                 if (jwtUtil.validateToken(token, userDetails) && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     //set the authentication detail in the security context for the next requests
                     SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 }
             }
         }
         catch (UsernameNotFoundException ex){
             request.setAttribute("usernameNotFoundException",ex);
         }

         //only when successful authentication is complete, proceed with processing of the request
        filterChain.doFilter(request,response);
    }
}
