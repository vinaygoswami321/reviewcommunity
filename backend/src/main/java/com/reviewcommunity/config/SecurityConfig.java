package com.reviewcommunity.config;


import com.reviewcommunity.service.impl.AdminUserDetailsService;
import com.reviewcommunity.service.impl.CustomUserDetailsService;
import com.reviewcommunity.util.AdminAuthenticationFilter;
import com.reviewcommunity.util.AuthenticationFilter;
import com.reviewcommunity.util.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AdminUserDetailsService adminUserDetailsService;
    @Autowired
    private AdminAuthenticationFilter adminAuthenticationFilter;

    /*
    *   Configures security rules and access permissions
    * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/user/**")
                .hasAuthority("USER")
                .antMatchers("/api/admin/**")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(adminAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*
    *   Configures web security for specifying access to endpoints
    * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/api/user/register","/api/user/login")
                .antMatchers("/api/admin/login","/api/admin/register")
                .antMatchers("/api/stats");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    *   Creates a password encoder
    *   return a bcrypt password encoder
    * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    *  Creates an Authentication Manager for user
    * */
    @Bean
    @Qualifier("userAuthenticationManager")
    public AuthenticationManager userAuthenticationManager() throws Exception{
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = new ProviderManager(authenticationProvider);
        return authenticationManager;
    }

    /*
    *   Creates an Authentication Manager for admin
    * */
    @Bean
    @Qualifier("adminAuthenticationManager")
    public AuthenticationManager adminAuthenticationManager() throws Exception{
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = new ProviderManager(authenticationProvider);
        return authenticationManager;
    }

}

