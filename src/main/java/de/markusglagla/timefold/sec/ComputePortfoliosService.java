package de.markusglagla.timefold.sec;

import ai.timefold.solver.core.api.solver.SolverManager;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ComputePortfoliosService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputePortfoliosService.class);

    private SolverManager<SecuritisationPlan, Long> solverManager;

    public ComputePortfoliosService(SolverManager<SecuritisationPlan, Long> solverManager) {
        this.solverManager = solverManager;
    }

    public void compute() throws ExecutionException, InterruptedException {

        SecuritisationPlan problem = loadProblem();
        var solution = solverManager.solve(1L, problem);

        printPortfolioSummary(solution.getFinalBestSolution());
        logger.info("Solution score: {}", solution.getFinalBestSolution().getScore());
    }

    private SecuritisationPlan loadProblem() {
        List<Portfolio> portfolios = TestDataGenerator.generatePortfolios();
        return new SecuritisationPlan(
                portfolios,
                TestDataGenerator.generateLoans(1000)
        );
    }

    private void printPortfolioSummary(SecuritisationPlan securitisationPlan) {
        logger.info("Job assignments");
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.getRenderer().setCWC(new CWC_LongestLine().add(20, 50).add(20, 50)); // Erste und zweite Spalte auf 20 Zeichen
        asciiTable.addRule();
        asciiTable.addRow("Name", "Max volume", "Count", "A Grade", "B Grade", "Volume");
        asciiTable.addRule();
        for (Portfolio pf : securitisationPlan.getPortfolios()) {
            asciiTable.addRow(pf.getName(), pf.getMaxVolume(),
                    countLoansForPortfolio(securitisationPlan, pf),
                    countAGradeLoansForPortfolio(securitisationPlan, pf),
                    countBGradeLoansForPortfolio(securitisationPlan, pf),
                    sumLoanAmountsForPortfolio(securitisationPlan, pf));
        }
        asciiTable.addRule();
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);
    }

    private BigDecimal sumLoanAmountsForPortfolio(SecuritisationPlan plan, Portfolio portfolio) {
        return plan.getLoans().stream()
                .filter(loan -> portfolio.getName().equals(loan.getAssignedPortfolioName()))
                .map(Loan::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private long countLoansForPortfolio(SecuritisationPlan plan, Portfolio portfolio) {
        return plan.getLoans().stream()
                .filter(loan -> portfolio.getName().equals(loan.getAssignedPortfolioName()))
                .count();
    }

    private long countAGradeLoansForPortfolio(SecuritisationPlan plan, Portfolio portfolio) {
        return plan.getLoans().stream()
                .filter(loan -> portfolio.getName().equals(loan.getAssignedPortfolioName()))
                .filter(loan -> Ratings.isAGrade(loan.getRating()))
                .count();
    }

    private long countBGradeLoansForPortfolio(SecuritisationPlan plan, Portfolio portfolio) {
        return plan.getLoans().stream()
                .filter(loan -> portfolio.getName().equals(loan.getAssignedPortfolioName()))
                .filter(loan -> Ratings.isBGrade(loan.getRating()))
                .count();
    }

}
