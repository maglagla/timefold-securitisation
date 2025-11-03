package de.markusglagla.timefold.sec;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.util.List;

@PlanningSolution
public class SecuritisationPlan {

    @ValueRangeProvider(id = "portfolioRange")
    private List<Portfolio> portfolios;

    @PlanningEntityCollectionProperty
    private List<Loan> loans;

    @PlanningScore
    private HardMediumSoftScore score;

    public SecuritisationPlan() {
    }

    public SecuritisationPlan(List<Portfolio> portfolios, List<Loan> loans) {
        this.portfolios = portfolios;
        this.loans = loans;
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }
}

