package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.User;
import com.fastturtle.redbusschemadesign.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
