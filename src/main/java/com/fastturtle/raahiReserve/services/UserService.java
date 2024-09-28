package com.fastturtle.raahiReserve.services;

import com.fastturtle.raahiReserve.models.User;
import com.fastturtle.raahiReserve.models.UserWallet;
import com.fastturtle.raahiReserve.repositories.UserRepository;
import com.fastturtle.raahiReserve.repositories.UserWalletRepository;
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
