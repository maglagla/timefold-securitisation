package de.markusglagla.timefold.sec;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    private static final String[] PORTFOLIO_NAMES = {"Portfolio A", "Portfolio B", "Portfolio C"};

    public static List<Portfolio> generatePortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        for (String name : PORTFOLIO_NAMES) {
            Portfolio p = new Portfolio(name, BigDecimal.valueOf(100_000_000)); // 100M
            portfolios.add(p);
        }
        return portfolios;
    }

    public static List<Loan> generateLoans(int count, List<Portfolio> portfolios) {
        List<Loan> loans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Loan loan = new Loan("L" + i, randomAmount(), randomRating(), randomBetween(12, 120), randomInterestRate());
            loans.add(loan);
        }
        return loans;
    }

    public static SecuritisationPlan generateInitialPlan(int loanCount) {
        List<Portfolio> portfolios = generatePortfolios();
        List<Loan> loans = generateLoans(loanCount, portfolios);
        return new SecuritisationPlan(portfolios, loans);
    }

    // --- Helpers ---
    private static BigDecimal randomAmount() {
        return BigDecimal.valueOf(randomBetween(100_000, 5_000_000));
    }

    private static BigDecimal randomInterestRate() {
        return BigDecimal.valueOf(randomBetween(300, 800)).divide(BigDecimal.valueOf(100)); // 3.00% – 8.00%
    }

    private static String randomRating() {
        return Ratings.RATINGS[randomBetween(0, Ratings.RATINGS.length - 1)];
    }

    private static int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

