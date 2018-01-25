package io.budgetapp.resource;


import io.budgetapp.BudgetApplication;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.modal.ErrorResponse;
import io.budgetapp.model.User;
import io.budgetapp.model.form.LoginForm;
import io.budgetapp.model.form.SignUpForm;
import io.budgetapp.model.form.user.Password;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class UserResourceIT extends ResourceIT {

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<>(BudgetApplication.class, resourceFilePath("config-test.yml"));

    @Override
    protected int getPort() {
        return RULE.getLocalPort();
    }

    @Test
    public void shouldAbleCreateUser() {

        // given
        SignUpForm signUp = new SignUpForm();
		ASTClass.instrum("Variable Declaration Statement","41");

        // when
        signUp.setUsername(randomEmail());
		ASTClass.instrum("Expression Statement","44");
        signUp.setPassword(randomAlphabets());
		ASTClass.instrum("Expression Statement","45");
        Response response = post(ResourceURL.USER, signUp);
		ASTClass.instrum("Variable Declaration Statement","46");

        // then
        assertCreated(response);
		ASTClass.instrum("Expression Statement","49");
        Assert.assertNotNull(response.getLocation());
		ASTClass.instrum("Expression Statement","50");
    }

    @Test
    public void shouldBeAbleValidateSignUpForm() {
        // given
        SignUpForm signUp = new SignUpForm();
		ASTClass.instrum("Variable Declaration Statement","56");

        // when
        Response response = post(ResourceURL.USER, signUp);
		ASTClass.instrum("Variable Declaration Statement","59");

        // then
        assertBadRequest(response);
		ASTClass.instrum("Expression Statement","62");
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
		ASTClass.instrum("Variable Declaration Statement","63");
        assertTrue(errorResponse.getErrors().keySet().containsAll(Arrays.asList("username", "password")));
		ASTClass.instrum("Expression Statement","64");
    }


    @Test
    public void shouldAbleChangePassword() {
        // given
        Password password = new Password();
		ASTClass.instrum("Variable Declaration Statement","71");
        password.setOriginal(defaultUser.getPassword());
		ASTClass.instrum("Expression Statement","72");
        password.setPassword(randomAlphabets());
		ASTClass.instrum("Expression Statement","73");
        password.setConfirm(password.getPassword());
		ASTClass.instrum("Expression Statement","74");

        // when
        put(Resources.CHANGE_PASSWORD, password);
		ASTClass.instrum("Expression Statement","77");
        LoginForm login = new LoginForm();
		ASTClass.instrum("Variable Declaration Statement","78");
        login.setUsername(defaultUser.getUsername());
		ASTClass.instrum("Expression Statement","79");
        login.setPassword(password.getPassword());
		ASTClass.instrum("Expression Statement","80");
        Response authResponse = post(Resources.USER_AUTH, login);
		ASTClass.instrum("Variable Declaration Statement","81");

        // then
        assertOk(authResponse);
		ASTClass.instrum("Expression Statement","84");
        Assert.assertNotNull(authResponse.readEntity(User.class).getToken());
		ASTClass.instrum("Expression Statement","getToken","authResponse.readEntity(User.class).getToken()","85");
    }
}
