package de.markusglagla.timefold.sec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TimefoldDemoApplication implements CommandLineRunner {

    private final ComputePortfoliosService computePortfoliosService;
    private final ApplicationContext context;

    public TimefoldDemoApplication(ComputePortfoliosService computePortfoliosService, ApplicationContext context) {
        this.computePortfoliosService = computePortfoliosService;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(TimefoldDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        computePortfoliosService.compute();
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
