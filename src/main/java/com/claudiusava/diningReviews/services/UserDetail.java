package com.claudiusava.diningReviews.services;

import com.claudiusava.diningReviews.model.Role;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.RoleRepository;
import com.claudiusava.diningReviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserDetail implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not exists by Username");
        }

        User user = userOptional.get();
        
        String userRole = null;
        
        for(Role role : user.getRoles()){
            userRole = role.getRoleName().replace("ROLE_","");
        }
        
        System.out.println("UserRole: " + userRole);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(userRole) // Define the user's roles/authorities here
                .build();

    }

    public static String getLoggedUserUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static java.util.Collection<? extends GrantedAuthority> getLoggedUserRole(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}