package de.markusglagla.timefold.sec;

import java.util.Arrays;

public class Ratings {

    public static final String[] RATINGS = {
            "AAA",
            "AA",
            "A",
            "BBB",
            "BB",
            "B",
            "CCC",
            "CC",
            "C",
            "D"
    };

    public static boolean isInvestmentGrade(String rating) {
        return Arrays.asList(Ratings.RATINGS).indexOf(rating) <= 5;
    }

    public static boolean isBGrade(String rating) {
        return "BBB".equals(rating) || "BB".equals(rating) || "B".equals(rating);
    }

    public static boolean isAGrade(String rating) {
        return "AAA".equals(rating) || "AA".equals(rating) || "A".equals(rating);
    }
}
