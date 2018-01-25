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
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
    }

    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(User.class, Category.class, Budget.class, BudgetType.class, Transaction.class, Recurring.class, AuthToken.class) {

        @Override
        protected Hibernate5Module createHibernate5Module() {
            Hibernate5Module module = super.createHibernate5Module();
			//ASTClass.instrum("Variable Declaration Statement","56");
			//ASTClass.instrum("Variable Declaration Statement","55");
			//ASTClass.instrum("Variable Declaration Statement","54");
			//ASTClass.instrum("Variable Declaration Statement","53");
            // allow @Transient JPA annotation process by Jackson
            module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
			//ASTClass.instrum("Expression Statement","61");
			//ASTClass.instrum("Expression Statement","59");
			//ASTClass.instrum("Expression Statement","57");
			//ASTClass.instrum("Expression Statement","55");
            module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
			//ASTClass.instrum("Expression Statement","65");
			//ASTClass.instrum("Expression Statement","62");
			//ASTClass.instrum("Expression Statement","59");
			//ASTClass.instrum("Expression Statement","56");
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
		//ASTClass.instrum("Variable Declaration Statement","73");
		//ASTClass.instrum("Variable Declaration Statement","77");
		//ASTClass.instrum("Variable Declaration Statement","81");
		//ASTClass.instrum("Variable Declaration Statement","85");

        // allow using Environment variable in yaml
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
		//ASTClass.instrum("Expression Statement","81");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","91");
		//ASTClass.instrum("Expression Statement","96");

        bootstrap.addBundle(migrationBundle);
		//ASTClass.instrum("Expression Statement","106");
		//ASTClass.instrum("Expression Statement","100");
		//ASTClass.instrum("Expression Statement","94");
		//ASTClass.instrum("Expression Statement","88");
        bootstrap.addBundle(hibernate);
		//ASTClass.instrum("Expression Statement","110");
		//ASTClass.instrum("Expression Statement","103");
		//ASTClass.instrum("Expression Statement","96");
		//ASTClass.instrum("Expression Statement","89");
        bootstrap.addBundle(new ConfiguredAssetsBundle("/app", "/app", "index.html"));
		//ASTClass.instrum("Expression Statement","90");
		//ASTClass.instrum("Expression Statement","98");
		//ASTClass.instrum("Expression Statement","106");
		//ASTClass.instrum("Expression Statement","114");
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {

        // password encoder
        final PasswordEncoder passwordEncoder = new PasswordEncoder();
		//ASTClass.instrum("Variable Declaration Statement","97");
		//ASTClass.instrum("Variable Declaration Statement","106");
		//ASTClass.instrum("Variable Declaration Statement","115");
		//ASTClass.instrum("Variable Declaration Statement","124");

        // DAO
        final CategoryDAO categoryDAO = new CategoryDAO(hibernate.getSessionFactory(), configuration);
		//ASTClass.instrum("Variable Declaration Statement","130");
		//ASTClass.instrum("Variable Declaration Statement","120");
		//ASTClass.instrum("Variable Declaration Statement","110");
		//ASTClass.instrum("Variable Declaration Statement","100");
        final BudgetDAO budgetDAO = new BudgetDAO(hibernate.getSessionFactory(), configuration);
		//ASTClass.instrum("Variable Declaration Statement","134");
		//ASTClass.instrum("Variable Declaration Statement","123");
		//ASTClass.instrum("Variable Declaration Statement","112");
		//ASTClass.instrum("Variable Declaration Statement","101");
        final BudgetTypeDAO budgetTypeDAO = new BudgetTypeDAO(hibernate.getSessionFactory());
		//ASTClass.instrum("Variable Declaration Statement","138");
		//ASTClass.instrum("Variable Declaration Statement","126");
		//ASTClass.instrum("Variable Declaration Statement","114");
		//ASTClass.instrum("Variable Declaration Statement","102");
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
		//ASTClass.instrum("Variable Declaration Statement","142");
		//ASTClass.instrum("Variable Declaration Statement","129");
		//ASTClass.instrum("Variable Declaration Statement","116");
		//ASTClass.instrum("Variable Declaration Statement","103");
        final TransactionDAO transactionDAO = new TransactionDAO(hibernate.getSessionFactory());
		//ASTClass.instrum("Variable Declaration Statement","146");
		//ASTClass.instrum("Variable Declaration Statement","132");
		//ASTClass.instrum("Variable Declaration Statement","118");
		//ASTClass.instrum("Variable Declaration Statement","104");
        final RecurringDAO recurringDAO = new RecurringDAO(hibernate.getSessionFactory());
		//ASTClass.instrum("Variable Declaration Statement","150");
		//ASTClass.instrum("Variable Declaration Statement","135");
		//ASTClass.instrum("Variable Declaration Statement","120");
		//ASTClass.instrum("Variable Declaration Statement","105");
        final AuthTokenDAO authTokenDAO = new AuthTokenDAO(hibernate.getSessionFactory());
		//ASTClass.instrum("Variable Declaration Statement","106");
		//ASTClass.instrum("Variable Declaration Statement","122");
		//ASTClass.instrum("Variable Declaration Statement","138");
		//ASTClass.instrum("Variable Declaration Statement","154");

        // service
        final FinanceService financeService = new FinanceService(userDAO, budgetDAO, budgetTypeDAO, categoryDAO, transactionDAO, recurringDAO, authTokenDAO, passwordEncoder);
		//ASTClass.instrum("Variable Declaration Statement","109");
		//ASTClass.instrum("Variable Declaration Statement","126");
		//ASTClass.instrum("Variable Declaration Statement","143");
		//ASTClass.instrum("Variable Declaration Statement","160");

        // jobs
        final RecurringJob recurringJob = new UnitOfWorkAwareProxyFactory(hibernate).create(RecurringJob.class, FinanceService.class, financeService);
		//ASTClass.instrum("Variable Declaration Statement","112");
		//ASTClass.instrum("Variable Declaration Statement","130");
		//ASTClass.instrum("Variable Declaration Statement","148");
		//ASTClass.instrum("Variable Declaration Statement","166");

        // resource
        environment.jersey().register(new UserResource(financeService));
		//ASTClass.instrum("Expression Statement","172");
		//ASTClass.instrum("Expression Statement","153");
		//ASTClass.instrum("Expression Statement","134");
		//ASTClass.instrum("Expression Statement","115");
        environment.jersey().register(new CategoryResource(financeService));
		//ASTClass.instrum("Expression Statement","176");
		//ASTClass.instrum("Expression Statement","156");
		//ASTClass.instrum("Expression Statement","136");
		//ASTClass.instrum("Expression Statement","116");
        environment.jersey().register(new BudgetResource(financeService));
		//ASTClass.instrum("Expression Statement","180");
		//ASTClass.instrum("Expression Statement","159");
		//ASTClass.instrum("Expression Statement","138");
		//ASTClass.instrum("Expression Statement","117");
        environment.jersey().register(new TransactionResource(financeService));
		//ASTClass.instrum("Expression Statement","184");
		//ASTClass.instrum("Expression Statement","162");
		//ASTClass.instrum("Expression Statement","140");
		//ASTClass.instrum("Expression Statement","118");
        environment.jersey().register(new RecurringResource(financeService));
		//ASTClass.instrum("Expression Statement","188");
		//ASTClass.instrum("Expression Statement","165");
		//ASTClass.instrum("Expression Statement","142");
		//ASTClass.instrum("Expression Statement","119");
        environment.jersey().register(new ReportResource(financeService));
		//ASTClass.instrum("Expression Statement","120");
		//ASTClass.instrum("Expression Statement","144");
		//ASTClass.instrum("Expression Statement","168");
		//ASTClass.instrum("Expression Statement","192");

        // health check
        environment.jersey().register(new HealthCheckResource(environment.healthChecks()));
		//ASTClass.instrum("Expression Statement","123");
		//ASTClass.instrum("Expression Statement","148");
		//ASTClass.instrum("Expression Statement","173");
		//ASTClass.instrum("Expression Statement","198");


        // managed
        environment.lifecycle().manage(new MigrationManaged(configuration));
		//ASTClass.instrum("Expression Statement","205");
		//ASTClass.instrum("Expression Statement","179");
		//ASTClass.instrum("Expression Statement","153");
		//ASTClass.instrum("Expression Statement","127");
        environment.lifecycle().manage(new JobsManaged(recurringJob));
		//ASTClass.instrum("Expression Statement","128");
		//ASTClass.instrum("Expression Statement","155");
		//ASTClass.instrum("Expression Statement","182");
		//ASTClass.instrum("Expression Statement","209");

        // auth
        TokenAuthenticator tokenAuthenticator = new UnitOfWorkAwareProxyFactory(hibernate).create(TokenAuthenticator.class, FinanceService.class, financeService);
		//ASTClass.instrum("Variable Declaration Statement","215");
		//ASTClass.instrum("Variable Declaration Statement","187");
		//ASTClass.instrum("Variable Declaration Statement","159");
		//ASTClass.instrum("Variable Declaration Statement","131");
        final OAuthCredentialAuthFilter<User> authFilter =
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(tokenAuthenticator)
                        .setPrefix("Bearer")
                        .setAuthorizer(new DefaultAuthorizer())
                        .setUnauthorizedHandler(new DefaultUnauthorizedHandler())
                        .buildAuthFilter();
		//ASTClass.instrum("Variable Declaration Statement","219");
		//ASTClass.instrum("Variable Declaration Statement","190");
		//ASTClass.instrum("Variable Declaration Statement","161");
		//ASTClass.instrum("Variable Declaration Statement","132");
        environment.jersey().register(RolesAllowedDynamicFeature.class);
		//ASTClass.instrum("Expression Statement","229");
		//ASTClass.instrum("Expression Statement","199");
		//ASTClass.instrum("Expression Statement","169");
		//ASTClass.instrum("Expression Statement","139");
        environment.jersey().register(new AuthDynamicFeature(authFilter));
		//ASTClass.instrum("Expression Statement","233");
		//ASTClass.instrum("Expression Statement","202");
		//ASTClass.instrum("Expression Statement","171");
		//ASTClass.instrum("Expression Statement","140");
        environment.jersey().register(new AuthValueFactoryProvider.Binder(User.class));
		//ASTClass.instrum("Expression Statement","141");
		//ASTClass.instrum("Expression Statement","173");
		//ASTClass.instrum("Expression Statement","205");
		//ASTClass.instrum("Expression Statement","237");

        // filters
        FilterRegistration.Dynamic urlRewriteFilter = environment.servlets().addFilter("rewriteFilter", UrlRewriteFilter.class);
		//ASTClass.instrum("Variable Declaration Statement","243");
		//ASTClass.instrum("Variable Declaration Statement","210");
		//ASTClass.instrum("Variable Declaration Statement","177");
		//ASTClass.instrum("Variable Declaration Statement","144");
        urlRewriteFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
		//ASTClass.instrum("Expression Statement","247");
		//ASTClass.instrum("Expression Statement","213");
		//ASTClass.instrum("Expression Statement","179");
		//ASTClass.instrum("Expression Statement","145");
        urlRewriteFilter.setInitParameter("confPath", "urlrewrite.xml");
		//ASTClass.instrum("Expression Statement","251");
		//ASTClass.instrum("Expression Statement","216");
		//ASTClass.instrum("Expression Statement","181");
		//ASTClass.instrum("Expression Statement","146");
        ASTClass.instrum( "hello","Hi");
        // only enable for dev
        // FilterRegistration.Dynamic filterSlow = environment.servlets().addFilter("slowFilter", SlowNetworkFilter.class);
        // filterSlow.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
		//ASTClass.instrum("Expression Statement","147");
		//ASTClass.instrum("Expression Statement","183");
		//ASTClass.instrum("Expression Statement","219");
		//ASTClass.instrum("Expression Statement","255");

        // exception mapper
        environment.jersey().register(new NotFoundExceptionMapper());
		//ASTClass.instrum("Expression Statement","264");
		//ASTClass.instrum("Expression Statement","227");
		//ASTClass.instrum("Expression Statement","190");
		//ASTClass.instrum("Expression Statement","153");
        environment.jersey().register(new DataConstraintExceptionMapper());
		//ASTClass.instrum("Expression Statement","268");
		//ASTClass.instrum("Expression Statement","230");
		//ASTClass.instrum("Expression Statement","192");
		//ASTClass.instrum("Expression Statement","154");
        environment.jersey().register(new ConstraintViolationExceptionMapper());
		//ASTClass.instrum("Expression Statement","272");
		//ASTClass.instrum("Expression Statement","233");
		//ASTClass.instrum("Expression Statement","194");
		//ASTClass.instrum("Expression Statement","155");
        environment.jersey().register(new SQLConstraintViolationExceptionMapper());
		//ASTClass.instrum("Expression Statement","SQLConstraintViolationExceptionMapper,",SQLConstraintViolationExceptionMapper","156");
		//ASTClass.instrum("Expression Statement","SQLConstraintViolationExceptionMapper,",SQLConstraintViolationExceptionMapper","196");
		//ASTClass.instrum("Expression Statement","SQLConstraintViolationExceptionMapper,",SQLConstraintViolationExceptionMapper","236");
		//ASTClass.instrum("Expression Statement","SQLConstraintViolationExceptionMapper,",SQLConstraintViolationExceptionMapper","276");

    }

}
