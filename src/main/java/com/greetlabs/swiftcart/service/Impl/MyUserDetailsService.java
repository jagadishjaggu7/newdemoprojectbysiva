package com.greetlabs.swiftcart.service.Impl;

import com.greetlabs.swiftcart.entity.User;
import com.greetlabs.swiftcart.entity.UserPrincipal;
import com.greetlabs.swiftcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byUserEmail = userRepository.findByUserEmail(email);
        if (byUserEmail.isPresent()) {
            return new UserPrincipal(byUserEmail.get());
        }
        else{
            throw new UsernameNotFoundException("User not found with email: " + email+" # Please try again ");
        }

    }
}
