package io.budgetapp.resource;


import io.budgetapp.BudgetApplication;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.model.Category;
import io.budgetapp.model.CategoryType;
import io.budgetapp.model.form.budget.AddBudgetForm;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;


/**
 *
 */
public class CategoryResourceIT extends ResourceIT {

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<>(BudgetApplication.class, resourceFilePath("config-test.yml"));

    @Override
    protected int getPort() {
        return RULE.getLocalPort();
    }

    @Test
    public void shouldAbleCreateCategory() {

        // given
        Category category = new Category();
		ASTClass.instrum("Variable Declaration Statement","35");
        category.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","36");
        category.setType(CategoryType.EXPENDITURE);
		ASTClass.instrum("Expression Statement","37");

        // when
        Response response = post(ResourceURL.CATEGORY, category);
		ASTClass.instrum("Variable Declaration Statement","40");

        // then
        assertCreated(response);
		ASTClass.instrum("Expression Statement","43");
        Assert.assertNotNull(response.getLocation());
		ASTClass.instrum("Expression Statement","44");
    }

    @Test
    public void shouldAbleFindValidCategory() {

        // given
        Category category = new Category();
		ASTClass.instrum("Variable Declaration Statement","51");
        category.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","52");
        category.setType(CategoryType.EXPENDITURE);
		ASTClass.instrum("Expression Statement","53");

        // when
        Response response = post(ResourceURL.CATEGORY, category);
		ASTClass.instrum("Variable Declaration Statement","56");

        // then
        Response newReponse = get(response.getLocation().getPath());
		ASTClass.instrum("Variable Declaration Statement","59");
        assertOk(newReponse);
		ASTClass.instrum("Expression Statement","60");
    }


    @Test
    public void shouldNotAbleDeleteCategoryWithChild() {

        // given
        Category category = new Category();
		ASTClass.instrum("Variable Declaration Statement","68");
        category.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","69");
        category.setType(CategoryType.EXPENDITURE);
		ASTClass.instrum("Expression Statement","70");

        // when
        Response response = post(ResourceURL.CATEGORY, category);
		ASTClass.instrum("Variable Declaration Statement","73");
        AddBudgetForm budget = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","74");
        budget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","75");
        budget.setCategoryId(identityResponse(response).getId());
		ASTClass.instrum("Expression Statement","76");
        post(ResourceURL.BUDGET, budget);
		ASTClass.instrum("Expression Statement","77");

        // then
        Response newReponse = delete(response.getLocation().getPath());
		ASTClass.instrum("Variable Declaration Statement","80");
        assertBadRequest(newReponse);
		ASTClass.instrum("Expression Statement","newReponse","assertBadRequest(newReponse)","81");
    }
}
