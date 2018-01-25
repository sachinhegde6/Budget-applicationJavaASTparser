package io.budgetapp.resource;

import io.budgetapp.BudgetApplication;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.modal.IdentityResponse;
import io.budgetapp.model.Budget;
import io.budgetapp.model.form.TransactionForm;
import io.budgetapp.model.form.budget.AddBudgetForm;
import io.budgetapp.model.form.budget.UpdateBudgetForm;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 */
public class BudgetResourceIT extends ResourceIT {

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<>(BudgetApplication.class, resourceFilePath("config-test.yml"));

    @Override
    protected int getPort() {
        return RULE.getLocalPort();
    }

    @Test
    public void shouldAbleToListBudgets() {

        // given user (created from ResourceIT)

        // when
        Response response = get(ResourceURL.BUDGET);
		ASTClass.instrum("Variable Declaration Statement","38");
        assertOk(response);
		ASTClass.instrum("Expression Statement","39");
        List<IdentityResponse> identityResponses = identityResponses(response);
		ASTClass.instrum("Variable Declaration Statement","40");

        // then
        Assert.assertTrue(identityResponses.size() >= 0);
		ASTClass.instrum("Expression Statement","43");
    }

    @Test
    public void shouldAbleCreateBudget() {

        // given
        AddBudgetForm budget = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","50");
        budget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","51");
        budget.setCategoryId(defaultCategory.getId());
		ASTClass.instrum("Expression Statement","52");

        // when
        Response response = post(ResourceURL.BUDGET, budget);
		ASTClass.instrum("Variable Declaration Statement","55");

        // then
        assertCreated(response);
		ASTClass.instrum("Expression Statement","58");
        Assert.assertNotNull(response.getLocation());
		ASTClass.instrum("Expression Statement","59");
    }

    @Test
    public void shouldBeAbleUpdateBudget() {
        // given
        AddBudgetForm budget = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","65");
        budget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","66");
        budget.setCategoryId(defaultCategory.getId());
		ASTClass.instrum("Expression Statement","67");
        Response createdResponse = post(ResourceURL.BUDGET, budget);
		ASTClass.instrum("Variable Declaration Statement","68");
        long budgetId = identityResponse(createdResponse).getId();
		ASTClass.instrum("Variable Declaration Statement","69");

        // when
        UpdateBudgetForm updateBudgetForm = new UpdateBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","72");
        updateBudgetForm.setId(budgetId);
		ASTClass.instrum("Expression Statement","73");
        updateBudgetForm.setName("Test");
		ASTClass.instrum("Expression Statement","74");
        updateBudgetForm.setProjected(100);
		ASTClass.instrum("Expression Statement","75");
        Response updateResponse = put("/api/budgets/" + budgetId, updateBudgetForm);
		ASTClass.instrum("Variable Declaration Statement","76");
        Budget updatedBudget = updateResponse.readEntity(Budget.class);
		ASTClass.instrum("Variable Declaration Statement","77");

        // then
        assertCreated(createdResponse);
		ASTClass.instrum("Expression Statement","80");
        Assert.assertNotNull(createdResponse.getLocation());
		ASTClass.instrum("Expression Statement","81");
        Assert.assertEquals("Test", updatedBudget.getName());
		ASTClass.instrum("Expression Statement","82");
        Assert.assertEquals(100, updatedBudget.getProjected(), 0.000);
		ASTClass.instrum("Expression Statement","83");
    }

    @Test
    public void shouldAbleFindValidBudget() {

        // given
        AddBudgetForm budget = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","90");
        budget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","91");
        budget.setCategoryId(1L);
		ASTClass.instrum("Expression Statement","92");

        // when
        Response response = post(ResourceURL.BUDGET, budget);
		ASTClass.instrum("Variable Declaration Statement","95");

        // then
        Response newReponse = get(response.getLocation().getPath());
		ASTClass.instrum("Variable Declaration Statement","98");
        assertOk(newReponse);
		ASTClass.instrum("Expression Statement","99");
    }

    @Test
    public void shouldNotAbleDeleteBudgetWithChild() {

        // given
        AddBudgetForm addBudgetForm = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","106");
        addBudgetForm.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","107");
        addBudgetForm.setCategoryId(1L);
		ASTClass.instrum("Expression Statement","108");

        // when
        Response response = post(ResourceURL.BUDGET, addBudgetForm);
		ASTClass.instrum("Variable Declaration Statement","111");
        TransactionForm transactionForm = new TransactionForm();
		ASTClass.instrum("Variable Declaration Statement","112");
        transactionForm.setAmount(10.00);
		ASTClass.instrum("Expression Statement","113");
        Budget budget = new Budget();
		ASTClass.instrum("Variable Declaration Statement","114");
        budget.setId(identityResponse(response).getId());
		ASTClass.instrum("Expression Statement","115");
        transactionForm.setBudget(budget);
		ASTClass.instrum("Expression Statement","116");
        post(ResourceURL.TRANSACTION, transactionForm);
		ASTClass.instrum("Expression Statement","117");

        // then
        Response newReponse = delete(response.getLocation().getPath());
		ASTClass.instrum("Variable Declaration Statement","120");
        assertBadRequest(newReponse);
		ASTClass.instrum("Expression Statement","newReponse","assertBadRequest(newReponse)","121");
    }

}
