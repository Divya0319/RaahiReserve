package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByUserName(String userName);

}
