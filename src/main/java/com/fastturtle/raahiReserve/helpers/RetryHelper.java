package com.fastturtle.raahiReserve.helpers;

import java.util.function.Supplier;

public class RetryHelper {

    public static<T> T executeWithRetry(Supplier<T> action, int maxAttempts, long delayInMillis) {
        int attempt = 1;
        while(attempt <= maxAttempts) {
            try {
                T result = action.get();
                System.out.println("✅ Success on attempt " + attempt);
                return result;
            } catch(Exception ex) {
                System.out.println("❌ Attempt " + attempt + " failed: " + ex.getMessage());
                if(attempt == maxAttempts) {
                    System.out.println("💥 All attempts failed.");
                    throw ex;
                }

                try {
                    Thread.sleep(delayInMillis);
                } catch(InterruptedException ignored) {}
                attempt++;
            }
        }

        return null;
    }
}
