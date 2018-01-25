package io.budgetapp.resource;

import io.budgetapp.BudgetApplication;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.model.RecurringType;
import io.budgetapp.model.form.TransactionForm;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 *
 */
public class RecurringResourceIT extends ResourceIT {


    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<>(BudgetApplication.class, resourceFilePath("config-test.yml"));

    @Override
    protected int getPort() {
        return RULE.getLocalPort();
    }


    @Test
    public void shouldAbleCreateRecurring() {

        // before
        Response before = get(ResourceURL.RECURRING);
		ASTClass.instrum("Variable Declaration Statement","34");
        int originalCount = identityResponses(before).size();
		ASTClass.instrum("Variable Declaration Statement","35");

        // given
        TransactionForm transaction = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","38");
        transaction.setAmount(10.00);
		ASTClass.instrum("Expression Statement","39");
        transaction.setRecurring(Boolean.TRUE);
		ASTClass.instrum("Expression Statement","40");
        transaction.setRecurringType(RecurringType.MONTHLY);
		ASTClass.instrum("Expression Statement","41");
        transaction.setBudget(defaultBudget);
		ASTClass.instrum("Expression Statement","42");

        // when
        Response response = post(ResourceURL.TRANSACTION, transaction);
		ASTClass.instrum("Variable Declaration Statement","45");

        // then
        assertCreated(response);
		ASTClass.instrum("Expression Statement","48");
        Assert.assertNotNull(response.getLocation());
		ASTClass.instrum("Expression Statement","49");

        Response after = get(ResourceURL.RECURRING);
		ASTClass.instrum("Variable Declaration Statement","51");
        int finalCount = identityResponses(after).size();
		ASTClass.instrum("Variable Declaration Statement","52");
        Assert.assertTrue(finalCount - originalCount - 1 == 0);
		ASTClass.instrum("Expression Statement","originalCount","finalCount - originalCount - 1","53");
    }
}
