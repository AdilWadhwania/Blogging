package com.example.blogging.user.management.service;

import com.example.blogging.user.management.model.user.projections.UserView;
import com.example.blogging.user.management.repo.RoleRepository;
import com.example.blogging.user.management.repo.UserRepo;
import com.example.blogging.user.management.model.Role;
import com.example.blogging.user.management.model.Roles;
import com.example.blogging.user.management.model.User;
import com.example.blogging.user.management.model.user.UserRegisterRequest;
import com.example.blogging.user.management.model.user.projections.RoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;
    private RoleRepository roleRepository;
    /**
     * T0 encode the password
     */
    private PasswordEncoder passwordEncoder;

    /**
     * To verify the user request on the
     * time of registration
     */
    private UserVerification verification;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserVerification verification) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.verification = verification;
    }

    public String addUser(UserRegisterRequest userRequest) {

        User user=null;
        String returnable="";
        try
        {
            if(verification.verifyUserRequest(userRequest))
            {
                user = new User();

                user.setUsername(userRequest.getUserName());
                user.setEmail(userRequest.getEmail());
                user.setFirstname(userRequest.getFirstName());
                user.setLastname(userRequest.getLastName());
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

                Optional<RoleView> roleView = roleRepository.findByRolename(Roles.USER.toString());

                if (roleView.isPresent()) {
                    RoleView view = roleView.get();
                    Role role = new Role(view.getId(), view.getRoleName());
                    List<Role> new_roles = new ArrayList<>();
                    new_roles.add(role);
                    user.setUserRoles(new_roles);
                } else {
                    throw new RoleNotFoundException("No role for user : " + userRequest.getUserName());
                }
                user.setCreated_time(new Timestamp(System.currentTimeMillis()));
                user.setUpdated_time(new Timestamp(System.currentTimeMillis()));

                userRepo.save(user);
                returnable=userRequest.getUserName();
            }
            else {
                returnable="Verification failed, Try again by correcting, "+verification.getVerificationFailedCause();
            }
        }
        catch (Exception exception)
        {
            returnable=userRequest.getUserName()+" registration failed due to exception : "
                    +exception.getMessage();
        }
        return returnable;
    }

    public boolean loginCheck(String user,String userEntered)
    {
        System.out.println();
        User passwordInDB=userRepo.getPasswordByName(user);
        if(userEntered.equals(passwordInDB))
            return true;
        else
            return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserView user=userRepo.findByUsername(username);

        if(user==null)
        {
            throw new UsernameNotFoundException("No user found with email");
        }

        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
                .username(user.getusername())
                .password(user.getPassword())
                .roles("USER")
                .build();

        return userDetails;
    }

}
