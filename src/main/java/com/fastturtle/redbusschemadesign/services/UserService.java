package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.User;
import com.fastturtle.redbusschemadesign.models.UserWallet;
import com.fastturtle.redbusschemadesign.repositories.UserRepository;
import com.fastturtle.redbusschemadesign.repositories.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserWalletRepository userWalletRepository) {
        this.userRepository = userRepository;
        this.userWalletRepository = userWalletRepository;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public List<User> findAll() {return userRepository.findAll();}

    public UserWallet getUserWalletByEmail(String email) {
        return userWalletRepository.findWalletByEmail(email);
    }
}
