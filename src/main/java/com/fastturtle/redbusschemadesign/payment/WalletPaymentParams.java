package com.fastturtle.redbusschemadesign.payment;

import com.fastturtle.redbusschemadesign.models.User;

public class WalletPaymentParams extends PaymentParams {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
