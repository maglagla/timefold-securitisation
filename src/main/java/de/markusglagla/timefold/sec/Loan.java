package de.markusglagla.timefold.sec;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import java.math.BigDecimal;

@PlanningEntity
public class Loan {
    @PlanningId
    private String id;
    private BigDecimal amount;
    private String rating;
    private int durationInMonths;
    private BigDecimal interestRate;

    @PlanningVariable(valueRangeProviderRefs = "portfolioRange", allowsUnassigned = true)
    private Portfolio assignedPortfolio;

    public Loan() {
        this.id = null;
        this.amount = BigDecimal.ZERO;
        this.rating = null;
        this.durationInMonths = 0;
        this.interestRate = BigDecimal.ZERO;
    }

    public Loan(String id, BigDecimal amount, String rating, int durationInMonths, BigDecimal interestRate) {
        this.id = id;
        this.amount = amount;
        this.rating = rating;
        this.durationInMonths = durationInMonths;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", rating='" + rating + '\'' +
                ", durationInMonths=" + durationInMonths +
                ", interestRate=" + interestRate +
                ", assignedPortfolio=" + assignedPortfolio +
                '}';
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getRating() {
        return rating;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Portfolio getAssignedPortfolio() {
        return assignedPortfolio;
    }

    public void setAssignedPortfolio(Portfolio assignedPortfolio) {
        this.assignedPortfolio = assignedPortfolio;
    }

    public String getAssignedPortfolioName() {
        return assignedPortfolio != null ? assignedPortfolio.getName() : "Unassigned";
    }

    public int countInvestmentGrade() {
        return Ratings.isInvestmentGrade(this.rating) ? 1 : 0;
    }

}

