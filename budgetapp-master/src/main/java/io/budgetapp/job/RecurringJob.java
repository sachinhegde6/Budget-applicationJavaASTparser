package io.budgetapp.job;

import io.budgetapp.service.FinanceService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RecurringJob implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecurringJob.class);

    private final FinanceService financeService;

    public RecurringJob(FinanceService financeService) {
        this.financeService = financeService;
		ASTClass.instrum("Expression Statement","18");
    }

    @UnitOfWork
    @Override
    public void run() {
        long start = System.currentTimeMillis();
		ASTClass.instrum("Variable Declaration Statement","24");
        LOGGER.debug("Start {} job", getName());
		ASTClass.instrum("Expression Statement","25");
        financeService.updateRecurrings();
		ASTClass.instrum("Expression Statement","26");
        LOGGER.debug("Complete {} job and took {}ms", getName(), System.currentTimeMillis() - start);
		ASTClass.instrum("Expression Statement","27");
    }

    private String getName() {
        return "Recurring job";
    }
}
