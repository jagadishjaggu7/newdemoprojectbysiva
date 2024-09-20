package com.greetlabs.swiftcart.controller;

import com.greetlabs.swiftcart.dto.LoginDto;
import com.greetlabs.swiftcart.dto.UserDto;
import com.greetlabs.swiftcart.service.LoginService;
import com.greetlabs.swiftcart.service.UpdatePasswordService;
import com.greetlabs.swiftcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/swift-cart")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UpdatePasswordService updatePasswordService;


    @PostMapping("/register")
    public String userRegister(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {

        String responseToken = loginService.userLoginVerification(loginDto);

        Map<String, String> response = new HashMap<>();
        response.put("token", responseToken);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/greet")
    public String greet() {
        return "Hello, User!";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestBody Map<String, String> requestBody) {

        String responseToken = updatePasswordService.userEmailVerificationForUpdatePassword(requestBody.get("userEmail"));
        Map<String, String> response = new HashMap<>();
        response.put("token", responseToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String token, @RequestBody Map<String,String> password) {
        boolean isTokenValid = updatePasswordService.validateTemporaryToken(token);
        if(isTokenValid){
            String result = updatePasswordService.updatePassword(token,password);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
    }
}
