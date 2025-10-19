package de.markusglagla.timefold.sec;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.*;

import java.util.Arrays;

public class PortfolioConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[] {
                doNotExceedMaxAmount(factory),
                maximizePortfolioAmount(factory),
                onlyAllowedRatings(factory),
                atLeastHalfHighRatedLoans(factory)
        };
    }

    private Constraint doNotExceedMaxAmount(ConstraintFactory factory) {
        return factory.forEach(Loan.class)
                .groupBy(Loan::getAssignedPortfolio, ConstraintCollectors.sumBigDecimal(Loan::getAmount))
                .filter((portfolio, total) -> total.compareTo(portfolio.getMaxVolume()) > 0)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Portfolio volume exceeded");
    }

    private Constraint maximizePortfolioAmount(ConstraintFactory factory) {
        return factory.forEach(Loan.class)
                .filter(loan -> loan.getAssignedPortfolio() != null)
                .groupBy(Loan::getAssignedPortfolio, ConstraintCollectors.sumBigDecimal(Loan::getAmount))
                .filter((portfolio, total) -> total.compareTo(portfolio.getMaxVolume()) <= 0)
                .reward(HardSoftScore.ONE_SOFT, (portfolio, total) -> total.intValue())
                .asConstraint("Maximize amount within portfolio bound");
    }

    private Constraint onlyAllowedRatings(ConstraintFactory factory) {
        return factory.forEach(Loan.class)
                .filter(loan -> {
                    int idx = Arrays.asList(Ratings.RATINGS).indexOf(loan.getRating());
                    return idx > 5;
                })
                .penalize(HardSoftScore.ONE_HARD, loan -> 1)
                .asConstraint("Only allowed ratings");
    }

    private Constraint atLeastHalfHighRatedLoans(ConstraintFactory factory) {
        return factory.forEach(Loan.class)
                .groupBy(Loan::getAssignedPortfolio,
                        ConstraintCollectors.count(),
                        ConstraintCollectors.sum(loan -> loan.countInvestmentGrade()))
                .filter((portfolio, totalCount, investmentGradeCount) -> investmentGradeCount * 2 < totalCount)
                .penalize(HardSoftScore.ONE_HARD, (portfolio, totalCount, investmentGradeCount) -> 1)
                .asConstraint("Mindestens 50% A, AA oder AAA");
    }

}
