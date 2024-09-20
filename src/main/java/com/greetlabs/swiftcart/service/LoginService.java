package com.greetlabs.swiftcart.service;

import com.greetlabs.swiftcart.dto.LoginDto;

public interface LoginService {
    String userLoginVerification(LoginDto loginDto);
}
