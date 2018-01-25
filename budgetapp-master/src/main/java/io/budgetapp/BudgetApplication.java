package io.budgetapp;

import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import io.budgetapp.application.ConstraintViolationExceptionMapper;
import io.budgetapp.application.DataConstraintExceptionMapper;
import io.budgetapp.application.NotFoundExceptionMapper;
import io.budgetapp.application.SQLConstraintViolationExceptionMapper;
import io.budgetapp.auth.DefaultAuthorizer;
import io.budgetapp.auth.DefaultUnauthorizedHandler;
import io.budgetapp.auth.TokenAuthenticator;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.crypto.PasswordEncoder;
import io.budgetapp.dao.*;
import io.budgetapp.job.RecurringJob;
import io.budgetapp.managed.JobsManaged;
import io.budgetapp.managed.MigrationManaged;
import io.budgetapp.model.*;
import io.budgetapp.resource.*;
import io.budgetapp.service.FinanceService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 *
 */
public class BudgetApplication extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new BudgetApplication().run(args);
		ASTClass.instrum("Expression Statement","46");
    }

    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(User.class, Category.class, Budget.class, BudgetType.class, Transaction.class, Recurring.class, AuthToken.class) {

        @Override
        protected Hibernate5Module createHibernate5Module() {
            Hibernate5Module module = super.createHibernate5Module();
			ASTClass.instrum("Variable Declaration Statement","53");
            // allow @Transient JPA annotation process by Jackson
            module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
			ASTClass.instrum("Expression Statement","55");
            module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
			ASTClass.instrum("Expression Statement","56");
            return module;
        }

        @Override
        public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "budget-app";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        MigrationsBundle<AppConfiguration> migrationBundle = new MigrationsBundle<AppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
		ASTClass.instrum("Variable Declaration Statement","73");

        // allow using Environment variable in yaml
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
		ASTClass.instrum("Expression Statement","81");

        bootstrap.addBundle(migrationBundle);
		ASTClass.instrum("Expression Statement","88");
        bootstrap.addBundle(hibernate);
		ASTClass.instrum("Expression Statement","89");
        bootstrap.addBundle(new ConfiguredAssetsBundle("/app", "/app", "index.html"));
		ASTClass.instrum("Expression Statement","90");
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {

        // password encoder
        final PasswordEncoder passwordEncoder = new PasswordEncoder();
		ASTClass.instrum("Variable Declaration Statement","97");

        // DAO
        final CategoryDAO categoryDAO = new CategoryDAO(hibernate.getSessionFactory(), configuration);
		ASTClass.instrum("Variable Declaration Statement","100");
        final BudgetDAO budgetDAO = new BudgetDAO(hibernate.getSessionFactory(), configuration);
		ASTClass.instrum("Variable Declaration Statement","101");
        final BudgetTypeDAO budgetTypeDAO = new BudgetTypeDAO(hibernate.getSessionFactory());
		ASTClass.instrum("Variable Declaration Statement","102");
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
		ASTClass.instrum("Variable Declaration Statement","103");
        final TransactionDAO transactionDAO = new TransactionDAO(hibernate.getSessionFactory());
		ASTClass.instrum("Variable Declaration Statement","104");
        final RecurringDAO recurringDAO = new RecurringDAO(hibernate.getSessionFactory());
		ASTClass.instrum("Variable Declaration Statement","105");
        final AuthTokenDAO authTokenDAO = new AuthTokenDAO(hibernate.getSessionFactory());
		ASTClass.instrum("Variable Declaration Statement","106");

        // service
        final FinanceService financeService = new FinanceService(userDAO, budgetDAO, budgetTypeDAO, categoryDAO, transactionDAO, recurringDAO, authTokenDAO, passwordEncoder);
		ASTClass.instrum("Variable Declaration Statement","109");

        // jobs
        final RecurringJob recurringJob = new UnitOfWorkAwareProxyFactory(hibernate).create(RecurringJob.class, FinanceService.class, financeService);
		ASTClass.instrum("Variable Declaration Statement","112");

        // resource
        environment.jersey().register(new UserResource(financeService));
		ASTClass.instrum("Expression Statement","115");
        environment.jersey().register(new CategoryResource(financeService));
		ASTClass.instrum("Expression Statement","116");
        environment.jersey().register(new BudgetResource(financeService));
		ASTClass.instrum("Expression Statement","117");
        environment.jersey().register(new TransactionResource(financeService));
		ASTClass.instrum("Expression Statement","118");
        environment.jersey().register(new RecurringResource(financeService));
		ASTClass.instrum("Expression Statement","119");
        environment.jersey().register(new ReportResource(financeService));
		ASTClass.instrum("Expression Statement","120");

        // health check
        environment.jersey().register(new HealthCheckResource(environment.healthChecks()));
		ASTClass.instrum("Expression Statement","123");


        // managed
        environment.lifecycle().manage(new MigrationManaged(configuration));
		ASTClass.instrum("Expression Statement","127");
        environment.lifecycle().manage(new JobsManaged(recurringJob));
		ASTClass.instrum("Expression Statement","128");

        // auth
        TokenAuthenticator tokenAuthenticator = new UnitOfWorkAwareProxyFactory(hibernate).create(TokenAuthenticator.class, FinanceService.class, financeService);
		ASTClass.instrum("Variable Declaration Statement","131");
        final OAuthCredentialAuthFilter<User> authFilter =
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(tokenAuthenticator)
                        .setPrefix("Bearer")
                        .setAuthorizer(new DefaultAuthorizer())
                        .setUnauthorizedHandler(new DefaultUnauthorizedHandler())
                        .buildAuthFilter();
		ASTClass.instrum("Variable Declaration Statement","132");
        environment.jersey().register(RolesAllowedDynamicFeature.class);
		ASTClass.instrum("Expression Statement","139");
        environment.jersey().register(new AuthDynamicFeature(authFilter));
		ASTClass.instrum("Expression Statement","140");
        environment.jersey().register(new AuthValueFactoryProvider.Binder(User.class));
		ASTClass.instrum("Expression Statement","141");

        // filters
        FilterRegistration.Dynamic urlRewriteFilter = environment.servlets().addFilter("rewriteFilter", UrlRewriteFilter.class);
		ASTClass.instrum("Variable Declaration Statement","144");
        urlRewriteFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
		ASTClass.instrum("Expression Statement","145");
        urlRewriteFilter.setInitParameter("confPath", "urlrewrite.xml");
		ASTClass.instrum("Expression Statement","146");
        ASTClass.instrum( "hello","Hi");
		ASTClass.instrum("Expression Statement","147");
        ASTClass.instrum( "hello","Hi","yo","fawsed");
		ASTClass.instrum("Expression Statement","148");

        // only enable for dev
        // FilterRegistration.Dynamic filterSlow = environment.servlets().addFilter("slowFilter", SlowNetworkFilter.class);
        // filterSlow.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");

        // exception mapper
        environment.jersey().register(new NotFoundExceptionMapper());
		ASTClass.instrum("Expression Statement","155");
        environment.jersey().register(new DataConstraintExceptionMapper());
		ASTClass.instrum("Expression Statement","156");
        environment.jersey().register(new ConstraintViolationExceptionMapper());
		ASTClass.instrum("Expression Statement","157");
        environment.jersey().register(new SQLConstraintViolationExceptionMapper());
		ASTClass.instrum("Expression Statement","SQLConstraintViolationExceptionMapper","SQLConstraintViolationExceptionMapper","158");

    }

}
