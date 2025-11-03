package de.markusglagla.timefold.sec;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    public static List<Portfolio> generatePortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(new Portfolio("Portfolio A", BigDecimal.valueOf(100_000_000)));
        portfolios.add(new Portfolio("Portfolio B", BigDecimal.valueOf(75_000_000)));
        portfolios.add(new Portfolio("Portfolio C", BigDecimal.valueOf(50_000_000)));
        return portfolios;
    }

    public static List<Loan> generateLoans(int count) {
        List<Loan> loans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Loan loan = new Loan("L" + i, randomAmount(), randomRating(), randomBetween(12, 120), randomInterestRate());
            loans.add(loan);
        }
        return loans;
    }

    // --- Helpers ---
    private static BigDecimal randomAmount() {
        return BigDecimal.valueOf(randomBetween(100_000, 5_000_000));
    }

    private static BigDecimal randomInterestRate() {
        return BigDecimal.valueOf(randomBetween(300, 800)).divide(BigDecimal.valueOf(100), RoundingMode.CEILING); // 3.00% – 8.00%
    }

    private static String randomRating() {
        return Ratings.RATINGS[randomBetween(0, Ratings.RATINGS.length - 1)];
    }

    private static int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

