package com.greetlabs.swiftcart.service.Impl;

import com.greetlabs.swiftcart.dto.LoginDto;
import com.greetlabs.swiftcart.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String userLoginVerification(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(),loginDto.getUserPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(loginDto.getUserEmail());
        return "Login failed!";
    }
    
}
