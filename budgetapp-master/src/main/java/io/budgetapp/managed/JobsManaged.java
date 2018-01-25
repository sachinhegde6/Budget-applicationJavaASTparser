package io.budgetapp.managed;

import io.budgetapp.job.RecurringJob;
import io.dropwizard.lifecycle.Managed;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class JobsManaged implements Managed {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RecurringJob recurringJob;

    public JobsManaged(RecurringJob recurringJob) {
        this.recurringJob = recurringJob;
		ASTClass.instrum("Expression Statement","19");
    }

    @Override
    public void start() throws Exception {
        scheduler.scheduleAtFixedRate(recurringJob, 0, 10, TimeUnit.SECONDS);
		ASTClass.instrum("Expression Statement","24");
    }

    @Override
    public void stop() throws Exception {
        scheduler.shutdown();
		ASTClass.instrum("Expression Statement","shutdown","scheduler.shutdown()","29");
    }
}
