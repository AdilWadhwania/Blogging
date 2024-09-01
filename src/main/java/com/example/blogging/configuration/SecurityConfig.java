package com.example.blogging.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>We have mainly three things to configure in spring security
 * <p>
 *     <h4>1) UserDetails service</h4>
 *         It is used to load the user details whether from in-memory, DAO or
 *         any other external device
 * </p>
 * <p>
 *     <h4>2) PasswordEncoder</h4>
 *     <p>
 *         A bean to specify the password encoding used for storing the hashed password
 *         and even when it is used to authenticate user same encoder is used
 *     </p>
 * </p>
 * <p>
 *     <h4>3) Security filter chain</h4>
 *     <p>
 *         <p>- It is the primary entry point to configure spring security settings.</p>
 *         <p>- We can use HttpSecurity to define authorization rules, form login and other
 *           security aspects.</p>
 *     </p>
 * </p>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(csrf->csrf.disable())//testing purpose disable it
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers("/users/service","/users/register"
                                ,"/users/login").permitAll())
                .httpBasic(Customizer.withDefaults())
                /*.formLogin(Customizer.withDefaults())*/
                .build();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


   /* @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
