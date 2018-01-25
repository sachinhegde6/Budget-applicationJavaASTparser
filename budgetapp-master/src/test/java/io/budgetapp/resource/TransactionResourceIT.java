package io.budgetapp.resource;


import io.budgetapp.BudgetApplication;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.modal.IdentityResponse;
import io.budgetapp.model.Budget;
import io.budgetapp.model.Category;
import io.budgetapp.model.CategoryType;
import io.budgetapp.model.form.TransactionForm;
import io.budgetapp.model.form.budget.AddBudgetForm;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;


/**
 *
 */
public class TransactionResourceIT extends ResourceIT {

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<>(BudgetApplication.class, resourceFilePath("config-test.yml"));

    @Override
    protected int getPort() {
        return RULE.getLocalPort();
    }

    @Test
    public void shouldAbleCreateTransaction() {

        // given
        TransactionForm transaction = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","39");
        transaction.setAmount(10.00);
		ASTClass.instrum("Expression Statement","40");
        transaction.setBudget(defaultBudget);
		ASTClass.instrum("Expression Statement","41");

        // when
        Response response = post(ResourceURL.TRANSACTION, transaction);
		ASTClass.instrum("Variable Declaration Statement","44");

        // then
        assertCreated(response);
		ASTClass.instrum("Expression Statement","47");
        Assert.assertNotNull(response.getLocation());
		ASTClass.instrum("Expression Statement","48");
    }

    @Test
    public void shouldAbleDeleteValidTransaction() {

        // given
        TransactionForm transaction = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","55");
        transaction.setAmount(10.00);
		ASTClass.instrum("Expression Statement","56");
        transaction.setBudget(defaultBudget);
		ASTClass.instrum("Expression Statement","57");
        Response response = post(ResourceURL.TRANSACTION, transaction);
		ASTClass.instrum("Variable Declaration Statement","58");
        IdentityResponse identityResponse = identityResponse(response);
		ASTClass.instrum("Variable Declaration Statement","59");

        // when
        Response deleteResponse = delete(ResourceURL.TRANSACTION + "/" + identityResponse.getId());
		ASTClass.instrum("Variable Declaration Statement","62");

        // then
        assertDeleted(deleteResponse);
		ASTClass.instrum("Expression Statement","65");
    }

    @Test
    public void shouldNotAbleDeleteInvalidTransaction() {

        // given
        long transactionId = Long.MAX_VALUE;
		ASTClass.instrum("Variable Declaration Statement","72");

        // when
        Response deleteResponse = delete("/api/transactions/" + transactionId);
		ASTClass.instrum("Variable Declaration Statement","75");

        // then
        assertNotFound(deleteResponse);
		ASTClass.instrum("Expression Statement","78");
    }

    @Test
    public void shouldAbleFindValidTransaction() {

        // given
        TransactionForm transaction = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","85");
        transaction.setAmount(10.00);
		ASTClass.instrum("Expression Statement","86");
        transaction.setBudget(defaultBudget);
		ASTClass.instrum("Expression Statement","87");

        // when
        Response response = post(ResourceURL.TRANSACTION, transaction);
		ASTClass.instrum("Variable Declaration Statement","90");

        // then
        Response newReponse = get(response.getLocation().getPath());
		ASTClass.instrum("Variable Declaration Statement","93");
        assertOk(newReponse);
		ASTClass.instrum("Expression Statement","94");
    }

    @Test
    public void shouldAbleFindTransactionsByBudget() {
        // given
        Category category = new Category();
		ASTClass.instrum("Variable Declaration Statement","100");
        category.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","101");
        category.setType(CategoryType.EXPENDITURE);
		ASTClass.instrum("Expression Statement","102");

        AddBudgetForm budget = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","104");
        budget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","105");

        TransactionForm transaction = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","107");
        transaction.setAmount(10.00);
		ASTClass.instrum("Expression Statement","108");

        // when
        Response categoryResponse = post(ResourceURL.CATEGORY, category);
		ASTClass.instrum("Variable Declaration Statement","111");
        Long categoryId = identityResponse(categoryResponse).getId();
		ASTClass.instrum("Variable Declaration Statement","112");
        budget.setCategoryId(categoryId);
		ASTClass.instrum("Expression Statement","113");

        Response budgetResponse = post(ResourceURL.BUDGET, budget);
		ASTClass.instrum("Variable Declaration Statement","115");
        Long budgetId = identityResponse(budgetResponse).getId();
		ASTClass.instrum("Variable Declaration Statement","116");

        transaction.setBudget(new Budget(budgetId));
		ASTClass.instrum("Expression Statement","118");
        post(ResourceURL.TRANSACTION, transaction);
		ASTClass.instrum("Expression Statement","119");

        // then
        Response newResponse = get("/api/budgets/" + budgetId + "/transactions");
		ASTClass.instrum("Variable Declaration Statement","122");
        List<IdentityResponse> ids = identityResponses(newResponse);
		ASTClass.instrum("Variable Declaration Statement","123");
        assertOk(newResponse);
		ASTClass.instrum("Expression Statement","124");
        Assert.assertEquals(1, ids.size());
		ASTClass.instrum("Expression Statement","size","ids.size()","125");
    }
}
