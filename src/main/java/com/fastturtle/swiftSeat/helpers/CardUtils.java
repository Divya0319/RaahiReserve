package com.fastturtle.swiftSeat.helpers;

public class CardUtils {

    private final String cardCompany;

    public CardUtils(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public static String getCardCompany(String cardNumber) {
        if (cardNumber.startsWith("4")) return "Visa";
        if (cardNumber.startsWith("1")) return "MasterCard";
        if (cardNumber.startsWith("5")) return "EasyShop";
        if (cardNumber.startsWith("6")) return "Discover";
        return "Invalid Card";
    }

    public long getCreditLimit() {
        return switch (cardCompany) {
            case "Visa" -> 70000;
            case "MasterCard" -> 93000;
            case "EasyShop" -> 30000;
            case "Discover" -> 85000;
            default -> 0;
        };
    }
}
