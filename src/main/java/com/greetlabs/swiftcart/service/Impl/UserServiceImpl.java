package com.greetlabs.swiftcart.service.Impl;


import com.greetlabs.swiftcart.dto.UserDto;
import com.greetlabs.swiftcart.entity.User;
import com.greetlabs.swiftcart.exception.UserAlreadyExistsException;
import com.greetlabs.swiftcart.repository.UserRepository;
import com.greetlabs.swiftcart.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public String createUser(UserDto userDto) {

        try {
            validateAccountExistence(userDto);
        }
        catch (IllegalArgumentException e){
            return e.getMessage();
        }

        userDto.setUserPassword(encoder.encode(userDto.getUserPassword()));

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        LOGGER.info("SAVED USER : "+savedUser.getUserEmail());
        return "Signup Success You Can Login Now!";
    }


    public void validateAccountExistence(UserDto userDto) {
        Optional<User> byUserEmail = userRepository.findByUserEmail(userDto.getUserEmail());
        Optional<User> byUserMobile = userRepository.findByUserMobile(userDto.getUserMobile());

        if (byUserEmail.isPresent() && byUserMobile.isPresent()) {
            throw new UserAlreadyExistsException("Account already exists with email: "
                    + byUserEmail.get().getUserEmail() + " and mobile: " + byUserMobile.get().getUserMobile());
        } else if (byUserMobile.isPresent()) {
            throw new UserAlreadyExistsException("Account already exists with mobile: " + byUserMobile.get().getUserMobile());
        } else if (byUserEmail.isPresent()) {
            throw new UserAlreadyExistsException("Account already exists with email: " + byUserEmail.get().getUserEmail());
        }
    }
}
