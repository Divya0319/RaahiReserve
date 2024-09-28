package com.fastturtle.raahiReserve.helpers.payment;

import com.fastturtle.raahiReserve.models.User;

public class WalletPaymentParams extends PaymentParams {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
