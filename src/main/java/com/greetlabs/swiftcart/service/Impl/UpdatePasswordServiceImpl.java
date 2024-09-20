package com.greetlabs.swiftcart.service.Impl;

import com.greetlabs.swiftcart.entity.TemporaryTokens;
import com.greetlabs.swiftcart.entity.User;
import com.greetlabs.swiftcart.exception.UserNotFoundException;
import com.greetlabs.swiftcart.repository.TemporaryTokensRepository;
import com.greetlabs.swiftcart.repository.UserRepository;
import com.greetlabs.swiftcart.service.UpdatePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UpdatePasswordServiceImpl implements UpdatePasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePasswordServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TemporaryTokensRepository temporaryTokensRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public String userEmailVerificationForUpdatePassword(String email) {
        Optional<User> byUserEmail = userRepository.findByUserEmail(email);
        if (byUserEmail.isPresent()){
            String token = jwtService.generateToken(byUserEmail.get().getUserEmail());
            TemporaryTokens temporaryTokens = new TemporaryTokens(email, token);
            LOGGER.info(temporaryTokensRepository.findByUserEmail(email).isPresent() + "");
            if(temporaryTokensRepository.findByUserEmail(email).isPresent()){
                UpdateTemporaryToken(email,token);
            }else {
                temporaryTokensRepository.save(temporaryTokens);
            }
            return token;
        }
        throw new UserNotFoundException("User Not Found with email " + email);
    }

    @Override
    public void UpdateTemporaryToken(String email, String token) {
        // Retrieve the record by email
        Optional<TemporaryTokens> byUserEmail = temporaryTokensRepository.findByUserEmail(email);

        if(byUserEmail.isPresent()) {
            // If a record exists for this email, update the token
            TemporaryTokens existingToken = byUserEmail.get();
            existingToken.setTemporaryToken(token);  // Update the token
            temporaryTokensRepository.save(existingToken);  // Save the updated entity
        }
    }

    @Override
    public boolean validateTemporaryToken(String token) {
        return temporaryTokensRepository.existsByTemporaryToken(token);
    }

    @Override
    public String updatePassword(String token, Map<String,String> password) {
        Optional<TemporaryTokens> byTemporaryToken = temporaryTokensRepository.findByTemporaryToken(token);
        if(byTemporaryToken.isPresent()){
            String email = byTemporaryToken.get().getUserEmail();
            Optional<User> byUserEmail = userRepository.findByUserEmail(email);
           User user = byUserEmail.get();

            user.setUserPassword(encoder.encode(password.get("userPassword")));
            userRepository.save(user);
            return "Password updated successfully";
        }
        return "There was an error with your credentials";
    }


}
