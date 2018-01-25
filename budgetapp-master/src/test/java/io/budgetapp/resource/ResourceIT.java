package io.budgetapp.resource;

import com.google.common.io.Resources;
import io.budgetapp.client.HTTPTokenClientFilter;
import io.budgetapp.modal.IdentityResponse;
import io.budgetapp.model.Budget;
import io.budgetapp.model.Category;
import io.budgetapp.model.CategoryType;
import io.budgetapp.model.User;
import io.budgetapp.model.form.SignUpForm;
import io.budgetapp.model.form.budget.AddBudgetForm;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.BeforeClass;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public abstract class ResourceIT {

    private static final Logger LOGGER = Logger.getLogger(ResourceIT.class.getName());

    protected static Client client;
    protected static User defaultUser;
    protected static Category defaultCategory;
    protected static Budget defaultBudget;

    @BeforeClass
    public static void before() {
        ClientConfig clientConfig = new ClientConfig();
		ASTClass.instrum("Variable Declaration Statement","44");
        clientConfig.register(new LoggingFeature(LOGGER, LoggingFeature.Verbosity.PAYLOAD_ANY));
		ASTClass.instrum("Expression Statement","45");
        client = ClientBuilder.newClient(clientConfig);
		ASTClass.instrum("Expression Statement","46");

        SignUpForm signUp = new SignUpForm();
		ASTClass.instrum("Variable Declaration Statement","48");

        signUp.setUsername(randomEmail());
		ASTClass.instrum("Expression Statement","50");
        signUp.setPassword(randomAlphabets());
		ASTClass.instrum("Expression Statement","51");
        post(ResourceURL.USER, signUp);
		ASTClass.instrum("Expression Statement","52");
        Response authResponse = post("/api/users/auth", signUp);
		ASTClass.instrum("Variable Declaration Statement","53");
        defaultUser = authResponse.readEntity(User.class);
		ASTClass.instrum("Expression Statement","54");
        defaultUser.setUsername(signUp.getUsername());
		ASTClass.instrum("Expression Statement","55");
        defaultUser.setPassword(signUp.getPassword());
		ASTClass.instrum("Expression Statement","56");

        client.register(new HTTPTokenClientFilter(defaultUser.getToken()));
		ASTClass.instrum("Expression Statement","58");

        defaultCategory = new Category();
		ASTClass.instrum("Expression Statement","60");
        defaultCategory.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","61");
        defaultCategory.setType(CategoryType.EXPENDITURE);
		ASTClass.instrum("Expression Statement","62");

        Response response = post(ResourceURL.CATEGORY, defaultCategory);
		ASTClass.instrum("Variable Declaration Statement","64");
        String location = response.getLocation().toString();
		ASTClass.instrum("Variable Declaration Statement","65");
        String[] raw = location.split("/");
		ASTClass.instrum("Variable Declaration Statement","66");
        defaultCategory.setId(Long.valueOf(raw[raw.length - 1]));
		ASTClass.instrum("Expression Statement","67");

        defaultBudget = new Budget();
		ASTClass.instrum("Expression Statement","69");
        defaultBudget.setName(randomAlphabets());
		ASTClass.instrum("Expression Statement","70");
        defaultBudget.setCategory(defaultCategory);
		ASTClass.instrum("Expression Statement","71");
        AddBudgetForm addBudgetForm = new AddBudgetForm();
		ASTClass.instrum("Variable Declaration Statement","72");
        addBudgetForm.setName(defaultBudget.getName());
		ASTClass.instrum("Expression Statement","73");
        addBudgetForm.setCategoryId(defaultCategory.getId());
		ASTClass.instrum("Expression Statement","74");
        response = post(ResourceURL.BUDGET, addBudgetForm);
		ASTClass.instrum("Expression Statement","75");
        location = response.getLocation().toString();
		ASTClass.instrum("Expression Statement","76");
        raw = location.split("/");
		ASTClass.instrum("Expression Statement","77");
        defaultBudget.setId(Long.valueOf(raw[raw.length - 1]));
		ASTClass.instrum("Expression Statement","78");

    }

    protected static Response post(String path, Object entity) {
        return client
                .target(getUrl(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildPost(Entity.json(entity)).invoke();
    }

    protected static Response put(String path, Object entity) {
        return client
                .target(getUrl(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildPut(Entity.json(entity)).invoke();
    }

    protected static Response delete(String path) {
        return client
                .target(getUrl(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildDelete().invoke();
    }

    protected Response get(String path) {
        return client
                .target(getUrl(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet().invoke();
    }

    protected IdentityResponse identityResponse(Response response) {
        return response.readEntity(IdentityResponse.class);
    }

    protected List<IdentityResponse> identityResponses(Response response) {
        return response.readEntity(new GenericType<List<IdentityResponse>>() {});
    }

    protected void assertCreated(Response response) {
        assertThat(response.getStatus(), is(201));
		ASTClass.instrum("Expression Statement","119");
    }

    protected void assertOk(Response response) {
        assertThat(response.getStatus(), is(200));
		ASTClass.instrum("Expression Statement","123");
    }

    protected void assertDeleted(Response response) {
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
		ASTClass.instrum("Expression Statement","127");
    }

    protected void assertNotFound(Response response) {
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
		ASTClass.instrum("Expression Statement","131");
    }

    protected void assertBadRequest(Response response) {
        assertThat(response.getStatus(), is(400));
		ASTClass.instrum("Expression Statement","135");
    }

    protected static String randomAlphabets() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected static String randomEmail() {
        return randomAlphabets() + "@" + randomAlphabets() + ".com";
    }

    protected static String getUrl() {
        return "http://localhost:9999";
    }

    protected abstract int getPort();

    protected static String getUrl(String path) {
        return getUrl() + path;
    }

    protected static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
