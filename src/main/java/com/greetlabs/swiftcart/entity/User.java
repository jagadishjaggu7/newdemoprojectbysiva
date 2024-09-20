package com.greetlabs.swiftcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * @author Venkata Siva Reddy Tumu
 * @date 16/09/2024
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Long userMobile;
    private Long alterMobile;
    private String userEmail;
    private String userPassword;
    private Boolean isAccepted;


}
