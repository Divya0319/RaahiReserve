package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {
}
