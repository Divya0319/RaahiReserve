package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.dtos.UserDTO;
import com.fastturtle.raahiReserve.models.User;
import com.fastturtle.raahiReserve.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class DBTestController {

    private final UserService userService;

    public DBTestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fetchall")
    public List<UserDTO> fetchAllUsers() {
        List<UserDTO> userDTOS = new ArrayList<>();

        List<User> users = userService.findAllWithRetry();

        if(users.isEmpty()) {
            return new ArrayList<>();
        }
        for(User user : users) {
            userDTOS.add(from(user));
        }

        return userDTOS;
    }

    private UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }
}
