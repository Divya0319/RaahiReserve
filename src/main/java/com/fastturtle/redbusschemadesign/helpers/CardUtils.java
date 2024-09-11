package com.fastturtle.redbusschemadesign.helpers;

public class CardUtils {

    public static String getCardCompany(String cardNumber) {
        if (cardNumber.startsWith("4")) return "Visa";
        if (cardNumber.startsWith("1")) return "Mastercard";
        if (cardNumber.startsWith("5")) return "EasyShop";
        if (cardNumber.startsWith("6")) return "Discover";
        return "Invalid Card";
    }
}
