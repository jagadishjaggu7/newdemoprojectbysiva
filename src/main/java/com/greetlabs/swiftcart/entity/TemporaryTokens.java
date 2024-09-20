package com.greetlabs.swiftcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TemporaryTokens")
public class TemporaryTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String temporaryToken;

    public TemporaryTokens(String userEmail, String temporaryToken) {
        this.userEmail = userEmail;
        this.temporaryToken = temporaryToken;
    }
}
