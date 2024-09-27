package com.fastturtle.swiftSeat.repositories;

import com.fastturtle.swiftSeat.models.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {

    @Query("SELECT uw FROM UserWallet uw WHERE uw.user.userId = :userId")
    UserWallet findByUserId(@Param("userId") Integer userId);

    @Query("SELECT uw.balance FROM UserWallet  uw WHERE uw.user.userName = :userName")
    BigInteger fetchBalanceForUser(@Param("userName") String userName);

    @Query("SELECT uw FROM UserWallet uw JOIN uw.user u WHERE u.email = :email")
    UserWallet findWalletByEmail(String email);
}
