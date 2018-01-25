package io.budgetapp.service;

import io.budgetapp.application.DataConstraintException;
import io.budgetapp.crypto.PasswordEncoder;
import io.budgetapp.dao.AuthTokenDAO;
import io.budgetapp.dao.CategoryDAO;
import io.budgetapp.dao.BudgetDAO;
import io.budgetapp.dao.BudgetTypeDAO;
import io.budgetapp.dao.RecurringDAO;
import io.budgetapp.dao.TransactionDAO;
import io.budgetapp.dao.UserDAO;
import io.budgetapp.model.AccountSummary;
import io.budgetapp.model.AuthToken;
import io.budgetapp.model.Budget;
import io.budgetapp.model.BudgetType;
import io.budgetapp.model.Category;
import io.budgetapp.model.CategoryType;
import io.budgetapp.model.Group;
import io.budgetapp.model.Point;
import io.budgetapp.model.PointType;
import io.budgetapp.model.Recurring;
import io.budgetapp.model.Transaction;
import io.budgetapp.model.UsageSummary;
import io.budgetapp.model.User;
import io.budgetapp.model.form.LoginForm;
import io.budgetapp.model.form.SignUpForm;
import io.budgetapp.model.form.TransactionForm;
import io.budgetapp.model.form.budget.AddBudgetForm;
import io.budgetapp.model.form.budget.UpdateBudgetForm;
import io.budgetapp.model.form.recurring.AddRecurringForm;
import io.budgetapp.model.form.report.SearchFilter;
import io.budgetapp.model.form.user.Password;
import io.budgetapp.model.form.user.Profile;
import io.budgetapp.util.Util;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class FinanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceService.class);
    private static final DateTimeFormatter SUMMARY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");

    private final UserDAO userDAO;
    private final BudgetDAO budgetDAO;
    private final BudgetTypeDAO budgetTypeDAO;
    private final CategoryDAO categoryDAO;
    private final TransactionDAO transactionDAO;
    private final RecurringDAO recurringDAO;
    private final AuthTokenDAO authTokenDAO;

    private final PasswordEncoder passwordEncoder;

    public FinanceService(UserDAO userDAO, BudgetDAO budgetDAO, BudgetTypeDAO budgetTypeDAO, CategoryDAO categoryDAO, TransactionDAO transactionDAO, RecurringDAO recurringDAO, AuthTokenDAO authTokenDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","66");
        this.budgetDAO = budgetDAO;
		//ASTClass.instrum("Expression Statement","70");
		//ASTClass.instrum("Expression Statement","69");
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","67");
        this.budgetTypeDAO = budgetTypeDAO;
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","70");
		//ASTClass.instrum("Expression Statement","68");
        this.categoryDAO = categoryDAO;
		//ASTClass.instrum("Expression Statement","78");
		//ASTClass.instrum("Expression Statement","75");
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","69");
        this.transactionDAO = transactionDAO;
		//ASTClass.instrum("Expression Statement","82");
		//ASTClass.instrum("Expression Statement","78");
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","70");
        this.recurringDAO = recurringDAO;
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","81");
		//ASTClass.instrum("Expression Statement","76");
		//ASTClass.instrum("Expression Statement","71");
        this.authTokenDAO = authTokenDAO;
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","78");
		//ASTClass.instrum("Expression Statement","84");
		//ASTClass.instrum("Expression Statement","90");

        this.passwordEncoder = passwordEncoder;
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","81");
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","95");
    }

    //==================================================================
    // USER
    //==================================================================
    public User addUser(SignUpForm signUp) {
        Optional<User> optional = userDAO.findByUsername(signUp.getUsername());
		//ASTClass.instrum("Variable Declaration Statement","105");
		//ASTClass.instrum("Variable Declaration Statement","97");
		//ASTClass.instrum("Variable Declaration Statement","89");
		//ASTClass.instrum("Variable Declaration Statement","81");
        if(optional.isPresent()) {
            throw new DataConstraintException("username", "Username already taken.");
        }
		//ASTClass.instrum("If Statement","109");
		//ASTClass.instrum("If Statement","100");
		//ASTClass.instrum("If Statement","91");
		//ASTClass.instrum("If Statement","82");
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","95");
		//ASTClass.instrum("Expression Statement","85");
        User user = userDAO.add(signUp);
		//ASTClass.instrum("Variable Declaration Statement","119");
		//ASTClass.instrum("Variable Declaration Statement","108");
		//ASTClass.instrum("Variable Declaration Statement","97");
		//ASTClass.instrum("Variable Declaration Statement","86");
        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","123");
		//ASTClass.instrum("Variable Declaration Statement","111");
		//ASTClass.instrum("Variable Declaration Statement","99");
		//ASTClass.instrum("Variable Declaration Statement","87");
        // init account
        initCategoriesAndBudgets(user, now.getMonthValue(), now.getYear());
		//ASTClass.instrum("Expression Statement","128");
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","102");
		//ASTClass.instrum("Expression Statement","89");
        return user;
    }

    public User update(User user, Profile profile) {
        user.setName(profile.getName());
		//ASTClass.instrum("Expression Statement","136");
		//ASTClass.instrum("Expression Statement","122");
		//ASTClass.instrum("Expression Statement","108");
		//ASTClass.instrum("Expression Statement","94");
        user.setCurrency(profile.getCurrency());
		//ASTClass.instrum("Expression Statement","140");
		//ASTClass.instrum("Expression Statement","125");
		//ASTClass.instrum("Expression Statement","110");
		//ASTClass.instrum("Expression Statement","95");
        userDAO.update(user);
		//ASTClass.instrum("Expression Statement","144");
		//ASTClass.instrum("Expression Statement","128");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","96");
        return user;
    }

    public void changePassword(User user, Password password) {
        User originalUser = userDAO.findById(user.getId());
		//ASTClass.instrum("Variable Declaration Statement","101");
		//ASTClass.instrum("Variable Declaration Statement","118");
		//ASTClass.instrum("Variable Declaration Statement","135");
		//ASTClass.instrum("Variable Declaration Statement","152");

        if(!Objects.equals(password.getPassword(), password.getConfirm())) {
            throw new DataConstraintException("confirm", "Confirm Password does not match");
        }
		//ASTClass.instrum("If Statement","103");
		//ASTClass.instrum("If Statement","121");
		//ASTClass.instrum("If Statement","139");
		//ASTClass.instrum("If Statement","157");

        if(!passwordEncoder.matches(password.getOriginal(), user.getPassword())) {
            throw new DataConstraintException("original", "Current Password does not match");
        }
		//ASTClass.instrum("If Statement","164");
		//ASTClass.instrum("If Statement","145");
		//ASTClass.instrum("If Statement","126");
		//ASTClass.instrum("If Statement","107");
        originalUser.setPassword(passwordEncoder.encode(password.getPassword()));
		//ASTClass.instrum("Expression Statement","170");
		//ASTClass.instrum("Expression Statement","150");
		//ASTClass.instrum("Expression Statement","130");
		//ASTClass.instrum("Expression Statement","110");
        userDAO.update(originalUser);
		//ASTClass.instrum("Expression Statement","111");
		//ASTClass.instrum("Expression Statement","132");
		//ASTClass.instrum("Expression Statement","153");
		//ASTClass.instrum("Expression Statement","174");
    }

    public Optional<User> findUserByToken(String token) {

        Optional<AuthToken> authToken = authTokenDAO.find(token);
		//ASTClass.instrum("Variable Declaration Statement","116");
		//ASTClass.instrum("Variable Declaration Statement","138");
		//ASTClass.instrum("Variable Declaration Statement","160");
		//ASTClass.instrum("Variable Declaration Statement","182");

        return authToken.map(AuthToken::getUser);
    }

    public Optional<User> login(LoginForm login) {
        Optional<User> optionalUser = userDAO.findByUsername(login.getUsername());
		//ASTClass.instrum("Variable Declaration Statement","191");
		//ASTClass.instrum("Variable Declaration Statement","168");
		//ASTClass.instrum("Variable Declaration Statement","145");
		//ASTClass.instrum("Variable Declaration Statement","122");
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
			//ASTClass.instrum("Variable Declaration Statement","196");
			//ASTClass.instrum("Variable Declaration Statement","172");
			//ASTClass.instrum("Variable Declaration Statement","148");
			//ASTClass.instrum("Variable Declaration Statement","124");
            if(passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                List<AuthToken> tokens = authTokenDAO.findByUser(user);
				//ASTClass.instrum("Variable Declaration Statement","201");
				//ASTClass.instrum("Variable Declaration Statement","176");
				//ASTClass.instrum("Variable Declaration Statement","151");
				//ASTClass.instrum("Variable Declaration Statement","126");
                if(tokens.isEmpty()) {
                    AuthToken token = authTokenDAO.add(optionalUser.get());
					//ASTClass.instrum("Variable Declaration Statement","206");
					//ASTClass.instrum("Variable Declaration Statement","180");
					//ASTClass.instrum("Variable Declaration Statement","154");
					//ASTClass.instrum("Variable Declaration Statement","128");
                    optionalUser.get().setToken(token.getToken());
					//ASTClass.instrum("Expression Statement","210");
					//ASTClass.instrum("Expression Statement","183");
					//ASTClass.instrum("Expression Statement","156");
					//ASTClass.instrum("Expression Statement","129");
                    return optionalUser;
                } else {
                    optionalUser.get().setToken(tokens.get(0).getToken());
					//ASTClass.instrum("Expression Statement","216");
					//ASTClass.instrum("Expression Statement","188");
					//ASTClass.instrum("Expression Statement","160");
					//ASTClass.instrum("Expression Statement","132");
                    return optionalUser;
                }
				//ASTClass.instrum("If Statement","127");
				//ASTClass.instrum("If Statement","153");
				//ASTClass.instrum("If Statement","179");
				//ASTClass.instrum("If Statement","205");
            }
			//ASTClass.instrum("If Statement","125");
			//ASTClass.instrum("If Statement","150");
			//ASTClass.instrum("If Statement","175");
			//ASTClass.instrum("If Statement","200");
        }
		//ASTClass.instrum("If Statement","123");
		//ASTClass.instrum("If Statement","147");
		//ASTClass.instrum("If Statement","171");
		//ASTClass.instrum("If Statement","195");

        return Optional.empty();
    }
    //==================================================================
    // END USER
    //==================================================================


    public AccountSummary findAccountSummaryByUser(User user, Integer month, Integer year) {
        if(month == null || year == null) {
            LocalDate now = LocalDate.now();
			//ASTClass.instrum("Variable Declaration Statement","243");
			//ASTClass.instrum("Variable Declaration Statement","211");
			//ASTClass.instrum("Variable Declaration Statement","179");
			//ASTClass.instrum("Variable Declaration Statement","147");
            month = now.getMonthValue();
			//ASTClass.instrum("Expression Statement","247");
			//ASTClass.instrum("Expression Statement","214");
			//ASTClass.instrum("Expression Statement","181");
			//ASTClass.instrum("Expression Statement","148");
            year = now.getYear();
			//ASTClass.instrum("Expression Statement","149");
			//ASTClass.instrum("Expression Statement","183");
			//ASTClass.instrum("Expression Statement","217");
			//ASTClass.instrum("Expression Statement","251");
        }
		//ASTClass.instrum("If Statement","242");
		//ASTClass.instrum("If Statement","210");
		//ASTClass.instrum("If Statement","178");
		//ASTClass.instrum("If Statement","146");
        LOGGER.debug("Find account summary {} {}-{}", user, month, year);
		//ASTClass.instrum("Expression Statement","259");
		//ASTClass.instrum("Expression Statement","223");
		//ASTClass.instrum("Expression Statement","187");
		//ASTClass.instrum("Expression Statement","151");
        AccountSummary accountSummary = new AccountSummary();
		//ASTClass.instrum("Variable Declaration Statement","263");
		//ASTClass.instrum("Variable Declaration Statement","226");
		//ASTClass.instrum("Variable Declaration Statement","189");
		//ASTClass.instrum("Variable Declaration Statement","152");
        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		//ASTClass.instrum("Variable Declaration Statement","153");
		//ASTClass.instrum("Variable Declaration Statement","191");
		//ASTClass.instrum("Variable Declaration Statement","229");
		//ASTClass.instrum("Variable Declaration Statement","267");

        // no budgets, first time access
        if(budgets.isEmpty()) {
            LOGGER.debug("First time access budgets {} {}-{}", user, month, year);
			//ASTClass.instrum("Expression Statement","274");
			//ASTClass.instrum("Expression Statement","235");
			//ASTClass.instrum("Expression Statement","196");
			//ASTClass.instrum("Expression Statement","157");
            initCategoriesAndBudgets(user, month, year);
			//ASTClass.instrum("Expression Statement","278");
			//ASTClass.instrum("Expression Statement","238");
			//ASTClass.instrum("Expression Statement","198");
			//ASTClass.instrum("Expression Statement","158");
            budgets = budgetDAO.findBudgets(user, month, year, true);
			//ASTClass.instrum("Expression Statement","159");
			//ASTClass.instrum("Expression Statement","200");
			//ASTClass.instrum("Expression Statement","241");
			//ASTClass.instrum("Expression Statement","282");
        }
		//ASTClass.instrum("If Statement","273");
		//ASTClass.instrum("If Statement","234");
		//ASTClass.instrum("If Statement","195");
		//ASTClass.instrum("If Statement","156");
        Map<Category, List<Budget>> grouped = budgets
                .stream()
                .collect(Collectors.groupingBy(Budget::getCategory));
		//ASTClass.instrum("Variable Declaration Statement","161");
		//ASTClass.instrum("Variable Declaration Statement","204");
		//ASTClass.instrum("Variable Declaration Statement","247");
		//ASTClass.instrum("Variable Declaration Statement","290");

        for(Map.Entry<Category, List<Budget>> entry: grouped.entrySet()) {
            Category category = entry.getKey();
			//ASTClass.instrum("Variable Declaration Statement","298");
			//ASTClass.instrum("Variable Declaration Statement","254");
			//ASTClass.instrum("Variable Declaration Statement","210");
			//ASTClass.instrum("Variable Declaration Statement","166");
            double budget = entry.getValue().stream().mapToDouble(Budget::getProjected).sum();
			//ASTClass.instrum("Variable Declaration Statement","302");
			//ASTClass.instrum("Variable Declaration Statement","257");
			//ASTClass.instrum("Variable Declaration Statement","212");
			//ASTClass.instrum("Variable Declaration Statement","167");
            double spent = entry.getValue().stream().mapToDouble(Budget::getActual).sum();
			//ASTClass.instrum("Variable Declaration Statement","306");
			//ASTClass.instrum("Variable Declaration Statement","260");
			//ASTClass.instrum("Variable Declaration Statement","214");
			//ASTClass.instrum("Variable Declaration Statement","168");
            Group group = new Group(category.getId(), category.getName());
			//ASTClass.instrum("Variable Declaration Statement","310");
			//ASTClass.instrum("Variable Declaration Statement","263");
			//ASTClass.instrum("Variable Declaration Statement","216");
			//ASTClass.instrum("Variable Declaration Statement","169");
            group.setType(category.getType());
			//ASTClass.instrum("Expression Statement","314");
			//ASTClass.instrum("Expression Statement","266");
			//ASTClass.instrum("Expression Statement","218");
			//ASTClass.instrum("Expression Statement","170");
            group.setBudget(budget);
			//ASTClass.instrum("Expression Statement","318");
			//ASTClass.instrum("Expression Statement","269");
			//ASTClass.instrum("Expression Statement","220");
			//ASTClass.instrum("Expression Statement","171");
            group.setSpent(spent);
			//ASTClass.instrum("Expression Statement","322");
			//ASTClass.instrum("Expression Statement","272");
			//ASTClass.instrum("Expression Statement","222");
			//ASTClass.instrum("Expression Statement","172");
            group.setBudgets(entry.getValue());
			//ASTClass.instrum("Expression Statement","326");
			//ASTClass.instrum("Expression Statement","275");
			//ASTClass.instrum("Expression Statement","224");
			//ASTClass.instrum("Expression Statement","173");
            accountSummary.getGroups().add(group);
			//ASTClass.instrum("Expression Statement","174");
			//ASTClass.instrum("Expression Statement","226");
			//ASTClass.instrum("Expression Statement","278");
			//ASTClass.instrum("Expression Statement","330");
        }

        accountSummary.getGroups().sort(Comparator.comparing(Group::getId));
		//ASTClass.instrum("Expression Statement","336");
		//ASTClass.instrum("Expression Statement","283");
		//ASTClass.instrum("Expression Statement","230");
		//ASTClass.instrum("Expression Statement","177");
        return accountSummary;
    }

    private void initCategoriesAndBudgets(User user, int month, int year) {
        Collection<Category> categories = categoryDAO.findCategories(user);
		//ASTClass.instrum("Variable Declaration Statement","344");
		//ASTClass.instrum("Variable Declaration Statement","290");
		//ASTClass.instrum("Variable Declaration Statement","236");
		//ASTClass.instrum("Variable Declaration Statement","182");
        // no categories, first time access
        if(categories.isEmpty()) {
            LOGGER.debug("Create default categories and budgets {} {}-{}", user, month, year);
			//ASTClass.instrum("Expression Statement","350");
			//ASTClass.instrum("Expression Statement","295");
			//ASTClass.instrum("Expression Statement","240");
			//ASTClass.instrum("Expression Statement","185");
            generateDefaultCategoriesAndBudgets(user, month, year);
			//ASTClass.instrum("Expression Statement","186");
			//ASTClass.instrum("Expression Statement","242");
			//ASTClass.instrum("Expression Statement","298");
			//ASTClass.instrum("Expression Statement","354");
        } else {
            LOGGER.debug("Copy budgets {} {}-{}", user, month, year);
			//ASTClass.instrum("Expression Statement","359");
			//ASTClass.instrum("Expression Statement","302");
			//ASTClass.instrum("Expression Statement","245");
			//ASTClass.instrum("Expression Statement","188");
            generateBudgets(user, month, year);
			//ASTClass.instrum("Expression Statement","189");
			//ASTClass.instrum("Expression Statement","247");
			//ASTClass.instrum("Expression Statement","305");
			//ASTClass.instrum("Expression Statement","363");
        }
		//ASTClass.instrum("If Statement","184");
		//ASTClass.instrum("If Statement","239");
		//ASTClass.instrum("If Statement","294");
		//ASTClass.instrum("If Statement","349");
    }

    public UsageSummary findUsageSummaryByUser(User user, Integer month, Integer year) {

        if(month == null || year == null) {
            LocalDate now = LocalDate.now();
			//ASTClass.instrum("Variable Declaration Statement","376");
			//ASTClass.instrum("Variable Declaration Statement","316");
			//ASTClass.instrum("Variable Declaration Statement","256");
			//ASTClass.instrum("Variable Declaration Statement","196");
            month = now.getMonthValue();
			//ASTClass.instrum("Expression Statement","380");
			//ASTClass.instrum("Expression Statement","319");
			//ASTClass.instrum("Expression Statement","258");
			//ASTClass.instrum("Expression Statement","197");
            year = now.getYear();
			//ASTClass.instrum("Expression Statement","198");
			//ASTClass.instrum("Expression Statement","260");
			//ASTClass.instrum("Expression Statement","322");
			//ASTClass.instrum("Expression Statement","384");
        }
		//ASTClass.instrum("If Statement","195");
		//ASTClass.instrum("If Statement","255");
		//ASTClass.instrum("If Statement","315");
		//ASTClass.instrum("If Statement","375");

        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		//ASTClass.instrum("Variable Declaration Statement","201");
		//ASTClass.instrum("Variable Declaration Statement","265");
		//ASTClass.instrum("Variable Declaration Statement","329");
		//ASTClass.instrum("Variable Declaration Statement","393");

        double income =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.INCOME)
                        .mapToDouble(Budget::getActual)
                        .sum();
		//ASTClass.instrum("Variable Declaration Statement","203");
		//ASTClass.instrum("Variable Declaration Statement","268");
		//ASTClass.instrum("Variable Declaration Statement","333");
		//ASTClass.instrum("Variable Declaration Statement","398");

        double budget =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.EXPENDITURE)
                        .mapToDouble(Budget::getProjected)
                        .sum();
		//ASTClass.instrum("Variable Declaration Statement","210");
		//ASTClass.instrum("Variable Declaration Statement","276");
		//ASTClass.instrum("Variable Declaration Statement","342");
		//ASTClass.instrum("Variable Declaration Statement","408");

        double spent =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.EXPENDITURE)
                        .mapToDouble(Budget::getActual)
                        .sum();
		//ASTClass.instrum("Variable Declaration Statement","418");
		//ASTClass.instrum("Variable Declaration Statement","351");
		//ASTClass.instrum("Variable Declaration Statement","284");
		//ASTClass.instrum("Variable Declaration Statement","217");
        return new UsageSummary(income, budget, spent);
    }

    private void generateDefaultCategoriesAndBudgets(User user, int month, int year) {
        Collection<Category> categories = categoryDAO.addDefaultCategories(user);
		//ASTClass.instrum("Variable Declaration Statement","431");
		//ASTClass.instrum("Variable Declaration Statement","363");
		//ASTClass.instrum("Variable Declaration Statement","295");
		//ASTClass.instrum("Variable Declaration Statement","227");
        Map<String, List<Budget>> defaultBudgets = budgetDAO.findDefaultBudgets();
		//ASTClass.instrum("Variable Declaration Statement","435");
		//ASTClass.instrum("Variable Declaration Statement","366");
		//ASTClass.instrum("Variable Declaration Statement","297");
		//ASTClass.instrum("Variable Declaration Statement","228");
        Date period = Util.yearMonthDate(month, year);
		//ASTClass.instrum("Variable Declaration Statement","439");
		//ASTClass.instrum("Variable Declaration Statement","369");
		//ASTClass.instrum("Variable Declaration Statement","299");
		//ASTClass.instrum("Variable Declaration Statement","229");
        for(Category category: categories) {
            List<Budget> budgets = defaultBudgets.get(category.getName());
			//ASTClass.instrum("Variable Declaration Statement","444");
			//ASTClass.instrum("Variable Declaration Statement","373");
			//ASTClass.instrum("Variable Declaration Statement","302");
			//ASTClass.instrum("Variable Declaration Statement","231");
            if(budgets != null) {
                for(Budget budget : budgets) {
                    BudgetType budgetType = budgetTypeDAO.addBudgetType();
					//ASTClass.instrum("Variable Declaration Statement","450");
					//ASTClass.instrum("Variable Declaration Statement","378");
					//ASTClass.instrum("Variable Declaration Statement","306");
					//ASTClass.instrum("Variable Declaration Statement","234");
                    Budget newBudget = new Budget();
					//ASTClass.instrum("Variable Declaration Statement","454");
					//ASTClass.instrum("Variable Declaration Statement","381");
					//ASTClass.instrum("Variable Declaration Statement","308");
					//ASTClass.instrum("Variable Declaration Statement","235");
                    newBudget.setName(budget.getName());
					//ASTClass.instrum("Expression Statement","458");
					//ASTClass.instrum("Expression Statement","384");
					//ASTClass.instrum("Expression Statement","310");
					//ASTClass.instrum("Expression Statement","236");
                    newBudget.setPeriod(period);
					//ASTClass.instrum("Expression Statement","462");
					//ASTClass.instrum("Expression Statement","387");
					//ASTClass.instrum("Expression Statement","312");
					//ASTClass.instrum("Expression Statement","237");
                    newBudget.setCategory(category);
					//ASTClass.instrum("Expression Statement","466");
					//ASTClass.instrum("Expression Statement","390");
					//ASTClass.instrum("Expression Statement","314");
					//ASTClass.instrum("Expression Statement","238");
                    newBudget.setBudgetType(budgetType);
					//ASTClass.instrum("Expression Statement","470");
					//ASTClass.instrum("Expression Statement","393");
					//ASTClass.instrum("Expression Statement","316");
					//ASTClass.instrum("Expression Statement","239");
                    budgetDAO.addBudget(user, newBudget);
					//ASTClass.instrum("Expression Statement","240");
					//ASTClass.instrum("Expression Statement","318");
					//ASTClass.instrum("Expression Statement","396");
					//ASTClass.instrum("Expression Statement","474");
                }
            }
			//ASTClass.instrum("If Statement","232");
			//ASTClass.instrum("If Statement","304");
			//ASTClass.instrum("If Statement","376");
			//ASTClass.instrum("If Statement","448");
        }
    }

    //==================================================================
    // BUDGET
    //==================================================================

    public Budget addBudget(User user, AddBudgetForm budgetForm) {
        BudgetType budgetType = budgetTypeDAO.addBudgetType();
		//ASTClass.instrum("Variable Declaration Statement","491");
		//ASTClass.instrum("Variable Declaration Statement","411");
		//ASTClass.instrum("Variable Declaration Statement","331");
		//ASTClass.instrum("Variable Declaration Statement","251");
        Budget budget = new Budget(budgetForm);
		//ASTClass.instrum("Variable Declaration Statement","495");
		//ASTClass.instrum("Variable Declaration Statement","414");
		//ASTClass.instrum("Variable Declaration Statement","333");
		//ASTClass.instrum("Variable Declaration Statement","252");
        budget.setBudgetType(budgetType);
		//ASTClass.instrum("Expression Statement","499");
		//ASTClass.instrum("Expression Statement","417");
		//ASTClass.instrum("Expression Statement","335");
		//ASTClass.instrum("Expression Statement","253");
        return budgetDAO.addBudget(user, budget);
    }

    public Budget updateBudget(User user, UpdateBudgetForm budgetForm) {
        Budget budget = budgetDAO.findById(user, budgetForm.getId());
		//ASTClass.instrum("Variable Declaration Statement","507");
		//ASTClass.instrum("Variable Declaration Statement","424");
		//ASTClass.instrum("Variable Declaration Statement","341");
		//ASTClass.instrum("Variable Declaration Statement","258");
        Category category = categoryDAO.findById(budget.getCategory().getId());
		//ASTClass.instrum("Variable Declaration Statement","511");
		//ASTClass.instrum("Variable Declaration Statement","427");
		//ASTClass.instrum("Variable Declaration Statement","343");
		//ASTClass.instrum("Variable Declaration Statement","259");
        budget.setName(budgetForm.getName());
		//ASTClass.instrum("Expression Statement","515");
		//ASTClass.instrum("Expression Statement","430");
		//ASTClass.instrum("Expression Statement","345");
		//ASTClass.instrum("Expression Statement","260");
        budget.setProjected(budgetForm.getProjected());
		//ASTClass.instrum("Expression Statement","519");
		//ASTClass.instrum("Expression Statement","433");
		//ASTClass.instrum("Expression Statement","347");
		//ASTClass.instrum("Expression Statement","261");
        // INCOME type allow user change actual without
        // add transactions
        if(category.getType() == CategoryType.INCOME) {
            budget.setActual(budgetForm.getActual());
			//ASTClass.instrum("Expression Statement","265");
			//ASTClass.instrum("Expression Statement","352");
			//ASTClass.instrum("Expression Statement","439");
			//ASTClass.instrum("Expression Statement","526");
        }
		//ASTClass.instrum("If Statement","525");
		//ASTClass.instrum("If Statement","438");
		//ASTClass.instrum("If Statement","351");
		//ASTClass.instrum("If Statement","264");
        budgetDAO.update(budget);
		//ASTClass.instrum("Expression Statement","534");
		//ASTClass.instrum("Expression Statement","445");
		//ASTClass.instrum("Expression Statement","356");
		//ASTClass.instrum("Expression Statement","267");
        return budget;
    }

    public void deleteBudget(User user, long budgetId) {
        Budget budget = budgetDAO.findById(user, budgetId);
		//ASTClass.instrum("Variable Declaration Statement","542");
		//ASTClass.instrum("Variable Declaration Statement","452");
		//ASTClass.instrum("Variable Declaration Statement","362");
		//ASTClass.instrum("Variable Declaration Statement","272");
        budgetDAO.delete(budget);
		//ASTClass.instrum("Expression Statement","273");
		//ASTClass.instrum("Expression Statement","364");
		//ASTClass.instrum("Expression Statement","455");
		//ASTClass.instrum("Expression Statement","546");
    }

    public List<Budget> findBudgetsByUser(User user) {
        return budgetDAO.findBudgets(user);
    }

    public Budget findBudgetById(User user, long budgetId) {
        return budgetDAO.findById(user, budgetId);
    }

    public List<Budget> findBudgetsByCategory(User user, long categoryId) {
        return budgetDAO.findByUserAndCategory(user, categoryId);
    }

    public List<String> findBudgetSuggestions(User user, String q) {
        return budgetDAO.findSuggestions(user, q);
    }

    private void generateBudgets(User user, int month, int year) {
        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","569");
		//ASTClass.instrum("Variable Declaration Statement","477");
		//ASTClass.instrum("Variable Declaration Statement","385");
		//ASTClass.instrum("Variable Declaration Statement","293");
        // use current month's budgets
        // when user navigate backward
        List<Budget> originalBudgets = budgetDAO.findBudgets(user, now.getMonthValue(), now.getYear(), false);
		//ASTClass.instrum("Variable Declaration Statement","575");
		//ASTClass.instrum("Variable Declaration Statement","482");
		//ASTClass.instrum("Variable Declaration Statement","389");
		//ASTClass.instrum("Variable Declaration Statement","296");
        // current month budget is empty
        if(originalBudgets.isEmpty()) {
            // use latest budget
            Date latestDate = budgetDAO.findLatestBudget(user);
			//ASTClass.instrum("Variable Declaration Statement","582");
			//ASTClass.instrum("Variable Declaration Statement","488");
			//ASTClass.instrum("Variable Declaration Statement","394");
			//ASTClass.instrum("Variable Declaration Statement","300");
            LocalDate date = latestDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//ASTClass.instrum("Variable Declaration Statement","586");
			//ASTClass.instrum("Variable Declaration Statement","491");
			//ASTClass.instrum("Variable Declaration Statement","396");
			//ASTClass.instrum("Variable Declaration Statement","301");
            originalBudgets = budgetDAO.findBudgets(user, date.getMonthValue(), date.getYear(), false);
			//ASTClass.instrum("Expression Statement","302");
			//ASTClass.instrum("Expression Statement","398");
			//ASTClass.instrum("Expression Statement","494");
			//ASTClass.instrum("Expression Statement","590");
        }
		//ASTClass.instrum("If Statement","580");
		//ASTClass.instrum("If Statement","486");
		//ASTClass.instrum("If Statement","392");
		//ASTClass.instrum("If Statement","298");
        Date period = Util.yearMonthDate(month, year);
		//ASTClass.instrum("Variable Declaration Statement","598");
		//ASTClass.instrum("Variable Declaration Statement","500");
		//ASTClass.instrum("Variable Declaration Statement","402");
		//ASTClass.instrum("Variable Declaration Statement","304");
        for(Budget budget : originalBudgets) {
            Budget newBudget = new Budget();
			//ASTClass.instrum("Variable Declaration Statement","603");
			//ASTClass.instrum("Variable Declaration Statement","504");
			//ASTClass.instrum("Variable Declaration Statement","405");
			//ASTClass.instrum("Variable Declaration Statement","306");
            newBudget.setName(budget.getName());
			//ASTClass.instrum("Expression Statement","607");
			//ASTClass.instrum("Expression Statement","507");
			//ASTClass.instrum("Expression Statement","407");
			//ASTClass.instrum("Expression Statement","307");
            newBudget.setProjected(budget.getProjected());
			//ASTClass.instrum("Expression Statement","611");
			//ASTClass.instrum("Expression Statement","510");
			//ASTClass.instrum("Expression Statement","409");
			//ASTClass.instrum("Expression Statement","308");
            newBudget.setPeriod(period);
			//ASTClass.instrum("Expression Statement","615");
			//ASTClass.instrum("Expression Statement","513");
			//ASTClass.instrum("Expression Statement","411");
			//ASTClass.instrum("Expression Statement","309");
            newBudget.setCategory(budget.getCategory());
			//ASTClass.instrum("Expression Statement","619");
			//ASTClass.instrum("Expression Statement","516");
			//ASTClass.instrum("Expression Statement","413");
			//ASTClass.instrum("Expression Statement","310");
            newBudget.setBudgetType(budget.getBudgetType());
			//ASTClass.instrum("Expression Statement","623");
			//ASTClass.instrum("Expression Statement","519");
			//ASTClass.instrum("Expression Statement","415");
			//ASTClass.instrum("Expression Statement","311");
            budgetDAO.addBudget(user, newBudget);
			//ASTClass.instrum("Expression Statement","312");
			//ASTClass.instrum("Expression Statement","417");
			//ASTClass.instrum("Expression Statement","522");
			//ASTClass.instrum("Expression Statement","627");
        }
    }

    //==================================================================
    // END BUDGET
    //==================================================================


    //==================================================================
    // RECURRING
    //==================================================================

    public Recurring addRecurring(User user, AddRecurringForm recurringForm) {

        // validation
        Date now = new Date();
		//ASTClass.instrum("Variable Declaration Statement","646");
		//ASTClass.instrum("Variable Declaration Statement","540");
		//ASTClass.instrum("Variable Declaration Statement","434");
		//ASTClass.instrum("Variable Declaration Statement","328");
        if(!Util.inMonth(recurringForm.getRecurringAt(), new Date())) {
            throw new DataConstraintException("recurringAt", "Recurring Date must within " + Util.toFriendlyMonthDisplay(now) + " " + (now.getYear() + 1900));
        }
		//ASTClass.instrum("If Statement","650");
		//ASTClass.instrum("If Statement","543");
		//ASTClass.instrum("If Statement","436");
		//ASTClass.instrum("If Statement","329");
        // end validation
        Budget budget = findBudgetById(user, recurringForm.getBudgetId());
		//ASTClass.instrum("Variable Declaration Statement","657");
		//ASTClass.instrum("Variable Declaration Statement","549");
		//ASTClass.instrum("Variable Declaration Statement","441");
		//ASTClass.instrum("Variable Declaration Statement","333");
        budget.setActual(budget.getActual() + recurringForm.getAmount());
		//ASTClass.instrum("Expression Statement","661");
		//ASTClass.instrum("Expression Statement","552");
		//ASTClass.instrum("Expression Statement","443");
		//ASTClass.instrum("Expression Statement","334");
        budgetDAO.update(budget);
		//ASTClass.instrum("Expression Statement","335");
		//ASTClass.instrum("Expression Statement","445");
		//ASTClass.instrum("Expression Statement","555");
		//ASTClass.instrum("Expression Statement","665");

        Recurring recurring = new Recurring();
		//ASTClass.instrum("Variable Declaration Statement","670");
		//ASTClass.instrum("Variable Declaration Statement","559");
		//ASTClass.instrum("Variable Declaration Statement","448");
		//ASTClass.instrum("Variable Declaration Statement","337");
        recurring.setAmount(recurringForm.getAmount());
		//ASTClass.instrum("Expression Statement","674");
		//ASTClass.instrum("Expression Statement","562");
		//ASTClass.instrum("Expression Statement","450");
		//ASTClass.instrum("Expression Statement","338");
        recurring.setLastRunAt(recurringForm.getRecurringAt());
		//ASTClass.instrum("Expression Statement","678");
		//ASTClass.instrum("Expression Statement","565");
		//ASTClass.instrum("Expression Statement","452");
		//ASTClass.instrum("Expression Statement","339");
        recurring.setRecurringType(recurringForm.getRecurringType());
		//ASTClass.instrum("Expression Statement","682");
		//ASTClass.instrum("Expression Statement","568");
		//ASTClass.instrum("Expression Statement","454");
		//ASTClass.instrum("Expression Statement","340");
        recurring.setBudgetType(budget.getBudgetType());
		//ASTClass.instrum("Expression Statement","686");
		//ASTClass.instrum("Expression Statement","571");
		//ASTClass.instrum("Expression Statement","456");
		//ASTClass.instrum("Expression Statement","341");
        recurring.setRemark(recurringForm.getRemark());
		//ASTClass.instrum("Expression Statement","690");
		//ASTClass.instrum("Expression Statement","574");
		//ASTClass.instrum("Expression Statement","458");
		//ASTClass.instrum("Expression Statement","342");
        recurring = recurringDAO.addRecurring(recurring);
		//ASTClass.instrum("Expression Statement","343");
		//ASTClass.instrum("Expression Statement","460");
		//ASTClass.instrum("Expression Statement","577");
		//ASTClass.instrum("Expression Statement","694");

        Transaction transaction = new Transaction();
		//ASTClass.instrum("Variable Declaration Statement","699");
		//ASTClass.instrum("Variable Declaration Statement","581");
		//ASTClass.instrum("Variable Declaration Statement","463");
		//ASTClass.instrum("Variable Declaration Statement","345");
        transaction.setName(budget.getName());
		//ASTClass.instrum("Expression Statement","703");
		//ASTClass.instrum("Expression Statement","584");
		//ASTClass.instrum("Expression Statement","465");
		//ASTClass.instrum("Expression Statement","346");
        transaction.setAmount(recurring.getAmount());
		//ASTClass.instrum("Expression Statement","707");
		//ASTClass.instrum("Expression Statement","587");
		//ASTClass.instrum("Expression Statement","467");
		//ASTClass.instrum("Expression Statement","347");
        transaction.setRemark(recurringForm.getRemark());
		//ASTClass.instrum("Expression Statement","711");
		//ASTClass.instrum("Expression Statement","590");
		//ASTClass.instrum("Expression Statement","469");
		//ASTClass.instrum("Expression Statement","348");
        transaction.setAuto(Boolean.TRUE);
		//ASTClass.instrum("Expression Statement","715");
		//ASTClass.instrum("Expression Statement","593");
		//ASTClass.instrum("Expression Statement","471");
		//ASTClass.instrum("Expression Statement","349");
        transaction.setTransactionOn(recurring.getLastRunAt());
		//ASTClass.instrum("Expression Statement","719");
		//ASTClass.instrum("Expression Statement","596");
		//ASTClass.instrum("Expression Statement","473");
		//ASTClass.instrum("Expression Statement","350");
        transaction.setBudget(budget);
		//ASTClass.instrum("Expression Statement","723");
		//ASTClass.instrum("Expression Statement","599");
		//ASTClass.instrum("Expression Statement","475");
		//ASTClass.instrum("Expression Statement","351");
        transaction.setRecurring(recurring);
		//ASTClass.instrum("Expression Statement","727");
		//ASTClass.instrum("Expression Statement","602");
		//ASTClass.instrum("Expression Statement","477");
		//ASTClass.instrum("Expression Statement","352");
        transactionDAO.addTransaction(transaction);
		//ASTClass.instrum("Expression Statement","353");
		//ASTClass.instrum("Expression Statement","479");
		//ASTClass.instrum("Expression Statement","605");
		//ASTClass.instrum("Expression Statement","731");


        return recurring;
    }

    public List<Recurring> findRecurrings(User user) {
        List<Recurring> results = recurringDAO.findRecurrings(user);
		//ASTClass.instrum("Variable Declaration Statement","741");
		//ASTClass.instrum("Variable Declaration Statement","614");
		//ASTClass.instrum("Variable Declaration Statement","487");
		//ASTClass.instrum("Variable Declaration Statement","360");
        // TODO: fix N + 1 query but now still OK
        results.forEach(this::populateRecurring);
		//ASTClass.instrum("Expression Statement","746");
		//ASTClass.instrum("Expression Statement","618");
		//ASTClass.instrum("Expression Statement","490");
		//ASTClass.instrum("Expression Statement","362");
        LOGGER.debug("Found recurrings {}", results);
		//ASTClass.instrum("Expression Statement","750");
		//ASTClass.instrum("Expression Statement","621");
		//ASTClass.instrum("Expression Statement","492");
		//ASTClass.instrum("Expression Statement","363");
        return results;
    }

    public void updateRecurrings() {
        LOGGER.debug("Begin update recurrings...");
		//ASTClass.instrum("Expression Statement","758");
		//ASTClass.instrum("Expression Statement","628");
		//ASTClass.instrum("Expression Statement","498");
		//ASTClass.instrum("Expression Statement","368");
        List<Recurring> recurrings = recurringDAO.findActiveRecurrings();
		//ASTClass.instrum("Variable Declaration Statement","762");
		//ASTClass.instrum("Variable Declaration Statement","631");
		//ASTClass.instrum("Variable Declaration Statement","500");
		//ASTClass.instrum("Variable Declaration Statement","369");
        LOGGER.debug("Found {} recurring(s) item to update", recurrings.size());
		//ASTClass.instrum("Expression Statement","766");
		//ASTClass.instrum("Expression Statement","634");
		//ASTClass.instrum("Expression Statement","502");
		//ASTClass.instrum("Expression Statement","370");
        for (Recurring recurring : recurrings) {

            // budget
            Budget budget = budgetDAO.findByBudgetType(recurring.getBudgetType().getId());
			//ASTClass.instrum("Variable Declaration Statement","773");
			//ASTClass.instrum("Variable Declaration Statement","640");
			//ASTClass.instrum("Variable Declaration Statement","507");
			//ASTClass.instrum("Variable Declaration Statement","374");
            budget.setActual(budget.getActual() + recurring.getAmount());
			//ASTClass.instrum("Expression Statement","777");
			//ASTClass.instrum("Expression Statement","643");
			//ASTClass.instrum("Expression Statement","509");
			//ASTClass.instrum("Expression Statement","375");
            budgetDAO.update(budget);
            // end budget
			//ASTClass.instrum("Expression Statement","376");
			//ASTClass.instrum("Expression Statement","511");
			//ASTClass.instrum("Expression Statement","646");
			//ASTClass.instrum("Expression Statement","781");

            // recurring
            recurring.setLastRunAt(new Date());
			//ASTClass.instrum("Expression Statement","788");
			//ASTClass.instrum("Expression Statement","652");
			//ASTClass.instrum("Expression Statement","516");
			//ASTClass.instrum("Expression Statement","380");
            recurringDAO.update(recurring);
            // end recurring
			//ASTClass.instrum("Expression Statement","381");
			//ASTClass.instrum("Expression Statement","518");
			//ASTClass.instrum("Expression Statement","655");
			//ASTClass.instrum("Expression Statement","792");

            // transaction
            Transaction transaction = new Transaction();
			//ASTClass.instrum("Variable Declaration Statement","799");
			//ASTClass.instrum("Variable Declaration Statement","661");
			//ASTClass.instrum("Variable Declaration Statement","523");
			//ASTClass.instrum("Variable Declaration Statement","385");
            transaction.setName(budget.getName());
			//ASTClass.instrum("Expression Statement","803");
			//ASTClass.instrum("Expression Statement","664");
			//ASTClass.instrum("Expression Statement","525");
			//ASTClass.instrum("Expression Statement","386");
            transaction.setAmount(recurring.getAmount());
			//ASTClass.instrum("Expression Statement","807");
			//ASTClass.instrum("Expression Statement","667");
			//ASTClass.instrum("Expression Statement","527");
			//ASTClass.instrum("Expression Statement","387");
            transaction.setRecurring(recurring);
			//ASTClass.instrum("Expression Statement","811");
			//ASTClass.instrum("Expression Statement","670");
			//ASTClass.instrum("Expression Statement","529");
			//ASTClass.instrum("Expression Statement","388");
            transaction.setRemark(recurring.getRecurringTypeDisplay() + " recurring for " + budget.getName());
			//ASTClass.instrum("Expression Statement","815");
			//ASTClass.instrum("Expression Statement","673");
			//ASTClass.instrum("Expression Statement","531");
			//ASTClass.instrum("Expression Statement","389");
            transaction.setAuto(true);
			//ASTClass.instrum("Expression Statement","819");
			//ASTClass.instrum("Expression Statement","676");
			//ASTClass.instrum("Expression Statement","533");
			//ASTClass.instrum("Expression Statement","390");
            transaction.setBudget(budget);
			//ASTClass.instrum("Expression Statement","823");
			//ASTClass.instrum("Expression Statement","679");
			//ASTClass.instrum("Expression Statement","535");
			//ASTClass.instrum("Expression Statement","391");
            transaction.setTransactionOn(new Date());
			//ASTClass.instrum("Expression Statement","827");
			//ASTClass.instrum("Expression Statement","682");
			//ASTClass.instrum("Expression Statement","537");
			//ASTClass.instrum("Expression Statement","392");
            transactionDAO.addTransaction(transaction);
            // end transaction
			//ASTClass.instrum("Expression Statement","393");
			//ASTClass.instrum("Expression Statement","539");
			//ASTClass.instrum("Expression Statement","685");
			//ASTClass.instrum("Expression Statement","831");

        }
        LOGGER.debug("Finish update recurrings...");
		//ASTClass.instrum("Expression Statement","397");
		//ASTClass.instrum("Expression Statement","544");
		//ASTClass.instrum("Expression Statement","691");
		//ASTClass.instrum("Expression Statement","838");
    }

    private void populateRecurring(Recurring recurring) {
        Budget budget = budgetDAO.findByBudgetType(recurring.getBudgetType().getId());
		//ASTClass.instrum("Variable Declaration Statement","845");
		//ASTClass.instrum("Variable Declaration Statement","697");
		//ASTClass.instrum("Variable Declaration Statement","549");
		//ASTClass.instrum("Variable Declaration Statement","401");
        recurring.setName(budget.getName());
		//ASTClass.instrum("Expression Statement","402");
		//ASTClass.instrum("Expression Statement","551");
		//ASTClass.instrum("Expression Statement","700");
		//ASTClass.instrum("Expression Statement","849");
    }

    public void deleteRecurring(User user, long recurringId) {
        Recurring recurring = recurringDAO.find(user, recurringId);
		//ASTClass.instrum("Variable Declaration Statement","856");
		//ASTClass.instrum("Variable Declaration Statement","706");
		//ASTClass.instrum("Variable Declaration Statement","556");
		//ASTClass.instrum("Variable Declaration Statement","406");
        recurringDAO.delete(recurring);
		//ASTClass.instrum("Expression Statement","407");
		//ASTClass.instrum("Expression Statement","558");
		//ASTClass.instrum("Expression Statement","709");
		//ASTClass.instrum("Expression Statement","860");
    }
    //==================================================================
    // END RECURRING
    //==================================================================


    //==================================================================
    // TRANSACTION
    //==================================================================
    public Transaction addTransaction(User user, TransactionForm transactionForm) {

        Budget budget = budgetDAO.findById(user, transactionForm.getBudget().getId());
		//ASTClass.instrum("Variable Declaration Statement","419");
		//ASTClass.instrum("Variable Declaration Statement","571");
		//ASTClass.instrum("Variable Declaration Statement","723");
		//ASTClass.instrum("Variable Declaration Statement","875");

        // validation
        if(transactionForm.getAmount() == 0) {
            throw new DataConstraintException("amount", "Amount is required");
        }
		//ASTClass.instrum("If Statement","422");
		//ASTClass.instrum("If Statement","575");
		//ASTClass.instrum("If Statement","728");
		//ASTClass.instrum("If Statement","881");

        if(Boolean.TRUE.equals(transactionForm.getRecurring()) && transactionForm.getRecurringType() == null) {
            throw new DataConstraintException("recurringType", "Recurring Type is required");
        }
		//ASTClass.instrum("If Statement","426");
		//ASTClass.instrum("If Statement","580");
		//ASTClass.instrum("If Statement","734");
		//ASTClass.instrum("If Statement","888");

        Date transactionOn = transactionForm.getTransactionOn();
		//ASTClass.instrum("Variable Declaration Statement","895");
		//ASTClass.instrum("Variable Declaration Statement","740");
		//ASTClass.instrum("Variable Declaration Statement","585");
		//ASTClass.instrum("Variable Declaration Statement","430");
        if(!Util.inMonth(transactionOn, budget.getPeriod())) {
            throw new DataConstraintException("transactionOn", "Transaction Date must within " + Util.toFriendlyMonthDisplay(budget.getPeriod()) + " " + (budget.getPeriod().getYear() + 1900));
        }
        // end validation
		//ASTClass.instrum("If Statement","431");
		//ASTClass.instrum("If Statement","587");
		//ASTClass.instrum("If Statement","743");
		//ASTClass.instrum("If Statement","899");


        budget.setActual(budget.getActual() + transactionForm.getAmount());
		//ASTClass.instrum("Expression Statement","908");
		//ASTClass.instrum("Expression Statement","751");
		//ASTClass.instrum("Expression Statement","594");
		//ASTClass.instrum("Expression Statement","437");
        budgetDAO.update(budget);
		//ASTClass.instrum("Expression Statement","438");
		//ASTClass.instrum("Expression Statement","596");
		//ASTClass.instrum("Expression Statement","754");
		//ASTClass.instrum("Expression Statement","912");

        Recurring recurring = new Recurring();
		//ASTClass.instrum("Variable Declaration Statement","917");
		//ASTClass.instrum("Variable Declaration Statement","758");
		//ASTClass.instrum("Variable Declaration Statement","599");
		//ASTClass.instrum("Variable Declaration Statement","440");
        if(Boolean.TRUE.equals(transactionForm.getRecurring())) {
            LOGGER.debug("Add recurring {} by {}", transactionForm, user);
			//ASTClass.instrum("Expression Statement","922");
			//ASTClass.instrum("Expression Statement","762");
			//ASTClass.instrum("Expression Statement","602");
			//ASTClass.instrum("Expression Statement","442");
            recurring.setAmount(transactionForm.getAmount());
			//ASTClass.instrum("Expression Statement","926");
			//ASTClass.instrum("Expression Statement","765");
			//ASTClass.instrum("Expression Statement","604");
			//ASTClass.instrum("Expression Statement","443");
            recurring.setRecurringType(transactionForm.getRecurringType());
			//ASTClass.instrum("Expression Statement","930");
			//ASTClass.instrum("Expression Statement","768");
			//ASTClass.instrum("Expression Statement","606");
			//ASTClass.instrum("Expression Statement","444");
            recurring.setBudgetType(budget.getBudgetType());
			//ASTClass.instrum("Expression Statement","934");
			//ASTClass.instrum("Expression Statement","771");
			//ASTClass.instrum("Expression Statement","608");
			//ASTClass.instrum("Expression Statement","445");
            recurring.setRemark(transactionForm.getRemark());
			//ASTClass.instrum("Expression Statement","938");
			//ASTClass.instrum("Expression Statement","774");
			//ASTClass.instrum("Expression Statement","610");
			//ASTClass.instrum("Expression Statement","446");
            recurring.setLastRunAt(transactionForm.getTransactionOn());
			//ASTClass.instrum("Expression Statement","942");
			//ASTClass.instrum("Expression Statement","777");
			//ASTClass.instrum("Expression Statement","612");
			//ASTClass.instrum("Expression Statement","447");
            recurringDAO.addRecurring(recurring);
			//ASTClass.instrum("Expression Statement","448");
			//ASTClass.instrum("Expression Statement","614");
			//ASTClass.instrum("Expression Statement","780");
			//ASTClass.instrum("Expression Statement","946");
        }
		//ASTClass.instrum("If Statement","441");
		//ASTClass.instrum("If Statement","601");
		//ASTClass.instrum("If Statement","761");
		//ASTClass.instrum("If Statement","921");

        Transaction transaction = new Transaction();
		//ASTClass.instrum("Variable Declaration Statement","955");
		//ASTClass.instrum("Variable Declaration Statement","787");
		//ASTClass.instrum("Variable Declaration Statement","619");
		//ASTClass.instrum("Variable Declaration Statement","451");
        transaction.setName(budget.getName());
		//ASTClass.instrum("Expression Statement","959");
		//ASTClass.instrum("Expression Statement","790");
		//ASTClass.instrum("Expression Statement","621");
		//ASTClass.instrum("Expression Statement","452");
        transaction.setAmount(transactionForm.getAmount());
		//ASTClass.instrum("Expression Statement","963");
		//ASTClass.instrum("Expression Statement","793");
		//ASTClass.instrum("Expression Statement","623");
		//ASTClass.instrum("Expression Statement","453");
        transaction.setRemark(transactionForm.getRemark());
		//ASTClass.instrum("Expression Statement","967");
		//ASTClass.instrum("Expression Statement","796");
		//ASTClass.instrum("Expression Statement","625");
		//ASTClass.instrum("Expression Statement","454");
        transaction.setAuto(Boolean.TRUE.equals(transactionForm.getRecurring()));
		//ASTClass.instrum("Expression Statement","971");
		//ASTClass.instrum("Expression Statement","799");
		//ASTClass.instrum("Expression Statement","627");
		//ASTClass.instrum("Expression Statement","455");
        transaction.setTransactionOn(transactionForm.getTransactionOn());
		//ASTClass.instrum("Expression Statement","975");
		//ASTClass.instrum("Expression Statement","802");
		//ASTClass.instrum("Expression Statement","629");
		//ASTClass.instrum("Expression Statement","456");
        transaction.setBudget(transactionForm.getBudget());
		//ASTClass.instrum("Expression Statement","979");
		//ASTClass.instrum("Expression Statement","805");
		//ASTClass.instrum("Expression Statement","631");
		//ASTClass.instrum("Expression Statement","457");
        if(Boolean.TRUE.equals(transactionForm.getRecurring())) {
            transaction.setRecurring(recurring);
			//ASTClass.instrum("Expression Statement","459");
			//ASTClass.instrum("Expression Statement","634");
			//ASTClass.instrum("Expression Statement","809");
			//ASTClass.instrum("Expression Statement","984");
        }
		//ASTClass.instrum("If Statement","458");
		//ASTClass.instrum("If Statement","633");
		//ASTClass.instrum("If Statement","808");
		//ASTClass.instrum("If Statement","983");

        return transactionDAO.addTransaction(transaction);
    }

    public boolean deleteTransaction(User user, long transactionId) {
        // only delete transaction that belong to that user
        Optional<Transaction> optional = transactionDAO.findById(user, transactionId);
		//ASTClass.instrum("Variable Declaration Statement","998");
		//ASTClass.instrum("Variable Declaration Statement","821");
		//ASTClass.instrum("Variable Declaration Statement","644");
		//ASTClass.instrum("Variable Declaration Statement","467");
        if(optional.isPresent()) {
            Transaction transaction = optional.get();
			//ASTClass.instrum("Variable Declaration Statement","1003");
			//ASTClass.instrum("Variable Declaration Statement","825");
			//ASTClass.instrum("Variable Declaration Statement","647");
			//ASTClass.instrum("Variable Declaration Statement","469");
            Budget budget = transaction.getBudget();
			//ASTClass.instrum("Variable Declaration Statement","1007");
			//ASTClass.instrum("Variable Declaration Statement","828");
			//ASTClass.instrum("Variable Declaration Statement","649");
			//ASTClass.instrum("Variable Declaration Statement","470");
            budget.setActual(budget.getActual() - transaction.getAmount());
			//ASTClass.instrum("Expression Statement","1011");
			//ASTClass.instrum("Expression Statement","831");
			//ASTClass.instrum("Expression Statement","651");
			//ASTClass.instrum("Expression Statement","471");
            transactionDAO.delete(transaction);
			//ASTClass.instrum("Expression Statement","1015");
			//ASTClass.instrum("Expression Statement","834");
			//ASTClass.instrum("Expression Statement","653");
			//ASTClass.instrum("Expression Statement","472");
            return true;
        }
		//ASTClass.instrum("If Statement","1002");
		//ASTClass.instrum("If Statement","824");
		//ASTClass.instrum("If Statement","646");
		//ASTClass.instrum("If Statement","468");
        return false;
    }

    public Transaction findTransactionById(long transactionId) {
        return transactionDAO.findById(transactionId);
    }

    public List<Transaction> findRecentTransactions(User user, Integer limit) {
        if(limit == null) {
            limit = 20;
			//ASTClass.instrum("Expression Statement","484");
			//ASTClass.instrum("Expression Statement","667");
			//ASTClass.instrum("Expression Statement","850");
			//ASTClass.instrum("Expression Statement","1033");
        }
		//ASTClass.instrum("If Statement","1032");
		//ASTClass.instrum("If Statement","849");
		//ASTClass.instrum("If Statement","666");
		//ASTClass.instrum("If Statement","483");
        return transactionDAO.find(user, limit);
    }

    public List<Transaction> findTodayRecurringsTransactions(User user) {
        SearchFilter filter = new SearchFilter();
		//ASTClass.instrum("Variable Declaration Statement","1045");
		//ASTClass.instrum("Variable Declaration Statement","860");
		//ASTClass.instrum("Variable Declaration Statement","675");
		//ASTClass.instrum("Variable Declaration Statement","490");
        filter.setStartOn(new Date());
		//ASTClass.instrum("Expression Statement","1049");
		//ASTClass.instrum("Expression Statement","863");
		//ASTClass.instrum("Expression Statement","677");
		//ASTClass.instrum("Expression Statement","491");
        filter.setEndOn(new Date());
		//ASTClass.instrum("Expression Statement","1053");
		//ASTClass.instrum("Expression Statement","866");
		//ASTClass.instrum("Expression Statement","679");
		//ASTClass.instrum("Expression Statement","492");
        filter.setAuto(Boolean.TRUE);
		//ASTClass.instrum("Expression Statement","1057");
		//ASTClass.instrum("Expression Statement","869");
		//ASTClass.instrum("Expression Statement","681");
		//ASTClass.instrum("Expression Statement","493");
        return transactionDAO.findTransactions(user, filter);
    }

    public List<Transaction> findTransactionsByRecurring(User user, long recurringId) {
        return transactionDAO.findByRecurring(user, recurringId);
    }

    public List<Transaction> findTransactions(User user, SearchFilter filter) {
        LOGGER.debug("Search transactions with {}", filter);
		//ASTClass.instrum("Expression Statement","1069");
		//ASTClass.instrum("Expression Statement","880");
		//ASTClass.instrum("Expression Statement","691");
		//ASTClass.instrum("Expression Statement","502");
        return transactionDAO.findTransactions(user, filter);
    }

    public List<Transaction> findTransactionsByBudget(User user, long budgetId) {
        return transactionDAO.findByBudget(user, budgetId);
    }

    public List<Point> findTransactionUsage(User user, Integer month, Integer year) {
        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","511");
		//ASTClass.instrum("Variable Declaration Statement","701");
		//ASTClass.instrum("Variable Declaration Statement","891");
		//ASTClass.instrum("Variable Declaration Statement","1081");

        if(month == null || year == null) {
            month = now.getMonthValue();
			//ASTClass.instrum("Expression Statement","1087");
			//ASTClass.instrum("Expression Statement","896");
			//ASTClass.instrum("Expression Statement","705");
			//ASTClass.instrum("Expression Statement","514");
            year = now.getYear();
			//ASTClass.instrum("Expression Statement","515");
			//ASTClass.instrum("Expression Statement","707");
			//ASTClass.instrum("Expression Statement","899");
			//ASTClass.instrum("Expression Statement","1091");
        }
		//ASTClass.instrum("If Statement","513");
		//ASTClass.instrum("If Statement","704");
		//ASTClass.instrum("If Statement","895");
		//ASTClass.instrum("If Statement","1086");

        List<Point> points = new ArrayList<>();
		//ASTClass.instrum("Variable Declaration Statement","518");
		//ASTClass.instrum("Variable Declaration Statement","712");
		//ASTClass.instrum("Variable Declaration Statement","906");
		//ASTClass.instrum("Variable Declaration Statement","1100");

        LocalDate begin = LocalDate.of(year, month, 1);
		//ASTClass.instrum("Variable Declaration Statement","520");
		//ASTClass.instrum("Variable Declaration Statement","715");
		//ASTClass.instrum("Variable Declaration Statement","910");
		//ASTClass.instrum("Variable Declaration Statement","1105");

        LocalDate ending = LocalDate.of(year, month, begin.lengthOfMonth());
		//ASTClass.instrum("Variable Declaration Statement","1110");
		//ASTClass.instrum("Variable Declaration Statement","914");
		//ASTClass.instrum("Variable Declaration Statement","718");
		//ASTClass.instrum("Variable Declaration Statement","522");
        if(now.getMonthValue() == month && now.getYear() == year) {
            ending = now;
			//ASTClass.instrum("Expression Statement","524");
			//ASTClass.instrum("Expression Statement","721");
			//ASTClass.instrum("Expression Statement","918");
			//ASTClass.instrum("Expression Statement","1115");
        }
		//ASTClass.instrum("If Statement","523");
		//ASTClass.instrum("If Statement","720");
		//ASTClass.instrum("If Statement","917");
		//ASTClass.instrum("If Statement","1114");

        // first 1-7 days show last 7 days's transactions instead of
        // show 1 or 2 days
        if(ending.getDayOfMonth() < 7) {
            begin = begin.minusDays(7 - ending.getDayOfMonth());
			//ASTClass.instrum("Expression Statement","530");
			//ASTClass.instrum("Expression Statement","729");
			//ASTClass.instrum("Expression Statement","928");
			//ASTClass.instrum("Expression Statement","1127");
        }
		//ASTClass.instrum("If Statement","529");
		//ASTClass.instrum("If Statement","728");
		//ASTClass.instrum("If Statement","927");
		//ASTClass.instrum("If Statement","1126");

        Instant instantStart = begin.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		//ASTClass.instrum("Variable Declaration Statement","1136");
		//ASTClass.instrum("Variable Declaration Statement","935");
		//ASTClass.instrum("Variable Declaration Statement","734");
		//ASTClass.instrum("Variable Declaration Statement","533");
        Date start = Date.from(instantStart);
		//ASTClass.instrum("Variable Declaration Statement","534");
		//ASTClass.instrum("Variable Declaration Statement","736");
		//ASTClass.instrum("Variable Declaration Statement","938");
		//ASTClass.instrum("Variable Declaration Statement","1140");

        Instant instantEnd = ending.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		//ASTClass.instrum("Variable Declaration Statement","1145");
		//ASTClass.instrum("Variable Declaration Statement","942");
		//ASTClass.instrum("Variable Declaration Statement","739");
		//ASTClass.instrum("Variable Declaration Statement","536");
        Date end = Date.from(instantEnd);
		//ASTClass.instrum("Variable Declaration Statement","537");
		//ASTClass.instrum("Variable Declaration Statement","741");
		//ASTClass.instrum("Variable Declaration Statement","945");
		//ASTClass.instrum("Variable Declaration Statement","1149");


        List<Transaction> transactions = transactionDAO.findByRange(user, start, end);
		//ASTClass.instrum("Variable Declaration Statement","540");
		//ASTClass.instrum("Variable Declaration Statement","745");
		//ASTClass.instrum("Variable Declaration Statement","950");
		//ASTClass.instrum("Variable Declaration Statement","1155");

        Map<Date, List<Transaction>> groups = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionOn, TreeMap::new, Collectors.toList()));
		//ASTClass.instrum("Variable Declaration Statement","542");
		//ASTClass.instrum("Variable Declaration Statement","748");
		//ASTClass.instrum("Variable Declaration Statement","954");
		//ASTClass.instrum("Variable Declaration Statement","1160");

        int days = Period.between(begin, ending).getDays() + 1;
		//ASTClass.instrum("Variable Declaration Statement","546");
		//ASTClass.instrum("Variable Declaration Statement","753");
		//ASTClass.instrum("Variable Declaration Statement","960");
		//ASTClass.instrum("Variable Declaration Statement","1167");

        for (int i = 0; i < days; i++) {
            LocalDate day = begin.plusDays(i);
			//ASTClass.instrum("Variable Declaration Statement","1173");
			//ASTClass.instrum("Variable Declaration Statement","965");
			//ASTClass.instrum("Variable Declaration Statement","757");
			//ASTClass.instrum("Variable Declaration Statement","549");
            Instant instantDay = day.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			//ASTClass.instrum("Variable Declaration Statement","1177");
			//ASTClass.instrum("Variable Declaration Statement","968");
			//ASTClass.instrum("Variable Declaration Statement","759");
			//ASTClass.instrum("Variable Declaration Statement","550");
            Date dayDate = Date.from(instantDay);
			//ASTClass.instrum("Variable Declaration Statement","1181");
			//ASTClass.instrum("Variable Declaration Statement","971");
			//ASTClass.instrum("Variable Declaration Statement","761");
			//ASTClass.instrum("Variable Declaration Statement","551");
            groups.putIfAbsent(dayDate, Collections.emptyList());
			//ASTClass.instrum("Expression Statement","552");
			//ASTClass.instrum("Expression Statement","763");
			//ASTClass.instrum("Expression Statement","974");
			//ASTClass.instrum("Expression Statement","1185");
        }
		//ASTClass.instrum("For Statement","548");
		//ASTClass.instrum("For Statement","756");
		//ASTClass.instrum("For Statement","964");
		//ASTClass.instrum("For Statement","1172");

        for (Map.Entry<Date, List<Transaction>> entry : groups.entrySet()) {
            double total = entry.getValue()
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
			//ASTClass.instrum("Variable Declaration Statement","556");
			//ASTClass.instrum("Variable Declaration Statement","769");
			//ASTClass.instrum("Variable Declaration Statement","982");
			//ASTClass.instrum("Variable Declaration Statement","1195");

            LocalDate res = Util.toLocalDate(entry.getKey());
			//ASTClass.instrum("Variable Declaration Statement","1203");
			//ASTClass.instrum("Variable Declaration Statement","989");
			//ASTClass.instrum("Variable Declaration Statement","775");
			//ASTClass.instrum("Variable Declaration Statement","561");
            Point point = new Point(SUMMARY_DATE_FORMATTER.format(res), entry.getKey().getTime(), total, PointType.TRANSACTIONS);
			//ASTClass.instrum("Variable Declaration Statement","1207");
			//ASTClass.instrum("Variable Declaration Statement","992");
			//ASTClass.instrum("Variable Declaration Statement","777");
			//ASTClass.instrum("Variable Declaration Statement","562");
            points.add(point);
			//ASTClass.instrum("Expression Statement","563");
			//ASTClass.instrum("Expression Statement","779");
			//ASTClass.instrum("Expression Statement","995");
			//ASTClass.instrum("Expression Statement","1211");
        }
        return points;
    }
    //==================================================================
    // END TRANSACTION
    //==================================================================


    //==================================================================
    // CATEGORY
    //==================================================================

    public List<Category> findCategories(User user) {
        return categoryDAO.findCategories(user);
    }

    public Category addCategory(User user, Category category) {
        return categoryDAO.addCategory(user, category);
    }

    public Category findCategoryById(long categoryId) {
        return categoryDAO.findById(categoryId);
    }

    public List<Point> findUsageByCategory(User user, Integer month, Integer year) {

        if(month == null || year == null) {
            LocalDate now = LocalDate.now();
			//ASTClass.instrum("Variable Declaration Statement","1242");
			//ASTClass.instrum("Variable Declaration Statement","1025");
			//ASTClass.instrum("Variable Declaration Statement","808");
			//ASTClass.instrum("Variable Declaration Statement","591");
            month = now.getMonthValue();
			//ASTClass.instrum("Expression Statement","1246");
			//ASTClass.instrum("Expression Statement","1028");
			//ASTClass.instrum("Expression Statement","810");
			//ASTClass.instrum("Expression Statement","592");
            year = now.getYear();
			//ASTClass.instrum("Expression Statement","593");
			//ASTClass.instrum("Expression Statement","812");
			//ASTClass.instrum("Expression Statement","1031");
			//ASTClass.instrum("Expression Statement","1250");
        }
		//ASTClass.instrum("If Statement","590");
		//ASTClass.instrum("If Statement","807");
		//ASTClass.instrum("If Statement","1024");
		//ASTClass.instrum("If Statement","1241");

        List<Point> points = new ArrayList<>();
		//ASTClass.instrum("Variable Declaration Statement","1259");
		//ASTClass.instrum("Variable Declaration Statement","1038");
		//ASTClass.instrum("Variable Declaration Statement","817");
		//ASTClass.instrum("Variable Declaration Statement","596");
        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		//ASTClass.instrum("Variable Declaration Statement","1263");
		//ASTClass.instrum("Variable Declaration Statement","1041");
		//ASTClass.instrum("Variable Declaration Statement","819");
		//ASTClass.instrum("Variable Declaration Statement","597");
        Map<Category, List<Budget>> groups = budgets
                .stream()
                .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                .collect(Collectors.groupingBy(Budget::getCategory));
		//ASTClass.instrum("Variable Declaration Statement","1267");
		//ASTClass.instrum("Variable Declaration Statement","1044");
		//ASTClass.instrum("Variable Declaration Statement","821");
		//ASTClass.instrum("Variable Declaration Statement","598");
        for (Map.Entry<Category, List<Budget>> entry : groups.entrySet()) {
            double total = entry.getValue()
                    .stream()
                    .mapToDouble(Budget::getActual)
                    .sum();
			//ASTClass.instrum("Variable Declaration Statement","1275");
			//ASTClass.instrum("Variable Declaration Statement","1051");
			//ASTClass.instrum("Variable Declaration Statement","827");
			//ASTClass.instrum("Variable Declaration Statement","603");
            Point point = new Point(entry.getKey().getName(), entry.getKey().getId(), total, PointType.CATEGORY);
			//ASTClass.instrum("Variable Declaration Statement","1282");
			//ASTClass.instrum("Variable Declaration Statement","1057");
			//ASTClass.instrum("Variable Declaration Statement","832");
			//ASTClass.instrum("Variable Declaration Statement","607");
            points.add(point);
			//ASTClass.instrum("Expression Statement","608");
			//ASTClass.instrum("Expression Statement","834");
			//ASTClass.instrum("Expression Statement","1060");
			//ASTClass.instrum("Expression Statement","1286");
        }

        points.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		//ASTClass.instrum("Expression Statement","1292");
		//ASTClass.instrum("Expression Statement","1065");
		//ASTClass.instrum("Expression Statement","838");
		//ASTClass.instrum("Expression Statement","611");
        return points;
    }

    public List<Point> findMonthlyTransactionUsage(User user) {
        List<Point> points = new ArrayList<>();
		//ASTClass.instrum("Variable Declaration Statement","1300");
		//ASTClass.instrum("Variable Declaration Statement","1072");
		//ASTClass.instrum("Variable Declaration Statement","844");
		//ASTClass.instrum("Variable Declaration Statement","616");
        LocalDate end = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","1304");
		//ASTClass.instrum("Variable Declaration Statement","1075");
		//ASTClass.instrum("Variable Declaration Statement","846");
		//ASTClass.instrum("Variable Declaration Statement","617");
        LocalDate start = end.minusMonths(6);
		//ASTClass.instrum("Variable Declaration Statement","1308");
		//ASTClass.instrum("Variable Declaration Statement","1078");
		//ASTClass.instrum("Variable Declaration Statement","848");
		//ASTClass.instrum("Variable Declaration Statement","618");
        List<Budget> budgets = budgetDAO.findByRange(user, start.getMonthValue(), start.getYear(), end.getMonthValue(), end.getYear());
		//ASTClass.instrum("Variable Declaration Statement","619");
		//ASTClass.instrum("Variable Declaration Statement","850");
		//ASTClass.instrum("Variable Declaration Statement","1081");
		//ASTClass.instrum("Variable Declaration Statement","1312");

        // group by period
        Map<Date, List<Budget>> groups = budgets
                .stream()
                .collect(Collectors.groupingBy(Budget::getPeriod, TreeMap::new, Collectors.toList()));
		//ASTClass.instrum("Variable Declaration Statement","622");
		//ASTClass.instrum("Variable Declaration Statement","854");
		//ASTClass.instrum("Variable Declaration Statement","1086");
		//ASTClass.instrum("Variable Declaration Statement","1318");

        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","1325");
		//ASTClass.instrum("Variable Declaration Statement","1092");
		//ASTClass.instrum("Variable Declaration Statement","859");
		//ASTClass.instrum("Variable Declaration Statement","626");
        // populate empty months, if any
        for (int i = 0; i < 6; i++) {
            LocalDate day = now.minusMonths(i).withDayOfMonth(1);
			//ASTClass.instrum("Variable Declaration Statement","1331");
			//ASTClass.instrum("Variable Declaration Statement","1097");
			//ASTClass.instrum("Variable Declaration Statement","863");
			//ASTClass.instrum("Variable Declaration Statement","629");
            groups.putIfAbsent(Util.toDate(day), Collections.emptyList());
			//ASTClass.instrum("Expression Statement","630");
			//ASTClass.instrum("Expression Statement","865");
			//ASTClass.instrum("Expression Statement","1100");
			//ASTClass.instrum("Expression Statement","1335");
        }
		//ASTClass.instrum("For Statement","628");
		//ASTClass.instrum("For Statement","862");
		//ASTClass.instrum("For Statement","1096");
		//ASTClass.instrum("For Statement","1330");

        // generate points
        for (Map.Entry<Date, List<Budget>> entry : groups.entrySet()) {
            // budget
            double budget = entry.getValue()
                    .stream()
                    .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                    .mapToDouble(Budget::getProjected)
                    .sum();
			//ASTClass.instrum("Variable Declaration Statement","636");
			//ASTClass.instrum("Variable Declaration Statement","873");
			//ASTClass.instrum("Variable Declaration Statement","1110");
			//ASTClass.instrum("Variable Declaration Statement","1347");

            // spending
            double spending = entry.getValue()
                    .stream()
                    .filter(b -> b.getActual() > 0)
                    .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                    .mapToDouble(Budget::getActual)
                    .sum();
			//ASTClass.instrum("Variable Declaration Statement","643");
			//ASTClass.instrum("Variable Declaration Statement","881");
			//ASTClass.instrum("Variable Declaration Statement","1119");
			//ASTClass.instrum("Variable Declaration Statement","1357");

            // refund
            double refund = entry.getValue()
                    .stream()
                    .filter(b -> b.getActual() < 0)
                    .mapToDouble(Budget::getActual)
                    .sum();
			//ASTClass.instrum("Variable Declaration Statement","651");
			//ASTClass.instrum("Variable Declaration Statement","890");
			//ASTClass.instrum("Variable Declaration Statement","1129");
			//ASTClass.instrum("Variable Declaration Statement","1368");

            String month = Util.toFriendlyMonthDisplay(entry.getKey());
			//ASTClass.instrum("Variable Declaration Statement","1377");
			//ASTClass.instrum("Variable Declaration Statement","1137");
			//ASTClass.instrum("Variable Declaration Statement","897");
			//ASTClass.instrum("Variable Declaration Statement","657");
            Point spendingPoint = new Point(month, entry.getKey().getTime(), spending, PointType.MONTHLY_SPEND);
			//ASTClass.instrum("Variable Declaration Statement","1381");
			//ASTClass.instrum("Variable Declaration Statement","1140");
			//ASTClass.instrum("Variable Declaration Statement","899");
			//ASTClass.instrum("Variable Declaration Statement","658");
            Point refundPoint = new Point(month, entry.getKey().getTime(), refund, PointType.MONTHLY_REFUND);
			//ASTClass.instrum("Variable Declaration Statement","1385");
			//ASTClass.instrum("Variable Declaration Statement","1143");
			//ASTClass.instrum("Variable Declaration Statement","901");
			//ASTClass.instrum("Variable Declaration Statement","659");
            Point budgetPoint = new Point(month, entry.getKey().getTime(), budget, PointType.MONTHLY_BUDGET);
			//ASTClass.instrum("Variable Declaration Statement","660");
			//ASTClass.instrum("Variable Declaration Statement","903");
			//ASTClass.instrum("Variable Declaration Statement","1146");
			//ASTClass.instrum("Variable Declaration Statement","1389");

            points.add(spendingPoint);
			//ASTClass.instrum("Expression Statement","1394");
			//ASTClass.instrum("Expression Statement","1150");
			//ASTClass.instrum("Expression Statement","906");
			//ASTClass.instrum("Expression Statement","662");
            points.add(refundPoint);
			//ASTClass.instrum("Expression Statement","1398");
			//ASTClass.instrum("Expression Statement","1153");
			//ASTClass.instrum("Expression Statement","908");
			//ASTClass.instrum("Expression Statement","663");
            points.add(budgetPoint);
			//ASTClass.instrum("Expression Statement","664");
			//ASTClass.instrum("Expression Statement","910");
			//ASTClass.instrum("Expression Statement","1156");
			//ASTClass.instrum("Expression Statement","1402");
        }
        return points;
    }

    public void deleteCategory(User user, long categoryId) {
        Category category = categoryDAO.find(user, categoryId);
		//ASTClass.instrum("Variable Declaration Statement","1411");
		//ASTClass.instrum("Variable Declaration Statement","1164");
		//ASTClass.instrum("Variable Declaration Statement","917");
		//ASTClass.instrum("Variable Declaration Statement","670");
        categoryDAO.delete(category);
		//ASTClass.instrum("Expression Statement","671");
		//ASTClass.instrum("Expression Statement","919");
		//ASTClass.instrum("Expression Statement","1167");
		//ASTClass.instrum("Expression Statement","1415");
    }

    public List<String> findCategorySuggestions(User user, String q) {
        return categoryDAO.findSuggestions(user, q);
    }

    //==================================================================
    // END CATEGORY
    //==================================================================
}
