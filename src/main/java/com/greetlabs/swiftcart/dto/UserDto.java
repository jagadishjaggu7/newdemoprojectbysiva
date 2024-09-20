package com.greetlabs.swiftcart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long userMobile;
    private Long alterMobile;
    private String userEmail;
    private String userPassword;
    private Boolean isAccepted;
}
