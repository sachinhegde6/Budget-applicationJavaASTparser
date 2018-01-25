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
		ASTClass.instrum("Expression Statement","66");
        this.budgetDAO = budgetDAO;
		ASTClass.instrum("Expression Statement","67");
        this.budgetTypeDAO = budgetTypeDAO;
		ASTClass.instrum("Expression Statement","68");
        this.categoryDAO = categoryDAO;
		ASTClass.instrum("Expression Statement","69");
        this.transactionDAO = transactionDAO;
		ASTClass.instrum("Expression Statement","70");
        this.recurringDAO = recurringDAO;
		ASTClass.instrum("Expression Statement","71");
        this.authTokenDAO = authTokenDAO;
		ASTClass.instrum("Expression Statement","72");

        this.passwordEncoder = passwordEncoder;
		ASTClass.instrum("Expression Statement","74");
    }

    //==================================================================
    // USER
    //==================================================================
    public User addUser(SignUpForm signUp) {
        Optional<User> optional = userDAO.findByUsername(signUp.getUsername());
		ASTClass.instrum("Variable Declaration Statement","81");
        if(optional.isPresent()) {
            throw new DataConstraintException("username", "Username already taken.");
        }
		ASTClass.instrum("If Statement","82");
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
		ASTClass.instrum("Expression Statement","85");
        User user = userDAO.add(signUp);
		ASTClass.instrum("Variable Declaration Statement","86");
        LocalDate now = LocalDate.now();
		ASTClass.instrum("Variable Declaration Statement","87");
        // init account
        initCategoriesAndBudgets(user, now.getMonthValue(), now.getYear());
		ASTClass.instrum("Expression Statement","89");
        return user;
    }

    public User update(User user, Profile profile) {
        user.setName(profile.getName());
		ASTClass.instrum("Expression Statement","94");
        user.setCurrency(profile.getCurrency());
		ASTClass.instrum("Expression Statement","95");
        userDAO.update(user);
		ASTClass.instrum("Expression Statement","96");
        return user;
    }

    public void changePassword(User user, Password password) {
        User originalUser = userDAO.findById(user.getId());
		ASTClass.instrum("Variable Declaration Statement","101");

        if(!Objects.equals(password.getPassword(), password.getConfirm())) {
            throw new DataConstraintException("confirm", "Confirm Password does not match");
        }
		ASTClass.instrum("If Statement","103");

        if(!passwordEncoder.matches(password.getOriginal(), user.getPassword())) {
            throw new DataConstraintException("original", "Current Password does not match");
        }
		ASTClass.instrum("If Statement","107");
        originalUser.setPassword(passwordEncoder.encode(password.getPassword()));
		ASTClass.instrum("Expression Statement","110");
        userDAO.update(originalUser);
		ASTClass.instrum("Expression Statement","111");
    }

    public Optional<User> findUserByToken(String token) {

        Optional<AuthToken> authToken = authTokenDAO.find(token);
		ASTClass.instrum("Variable Declaration Statement","116");

        return authToken.map(AuthToken::getUser);
    }

    public Optional<User> login(LoginForm login) {
        Optional<User> optionalUser = userDAO.findByUsername(login.getUsername());
		ASTClass.instrum("Variable Declaration Statement","122");
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
			ASTClass.instrum("Variable Declaration Statement","124");
            if(passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                List<AuthToken> tokens = authTokenDAO.findByUser(user);
				ASTClass.instrum("Variable Declaration Statement","126");
                if(tokens.isEmpty()) {
                    AuthToken token = authTokenDAO.add(optionalUser.get());
					ASTClass.instrum("Variable Declaration Statement","128");
                    optionalUser.get().setToken(token.getToken());
					ASTClass.instrum("Expression Statement","129");
                    return optionalUser;
                } else {
                    optionalUser.get().setToken(tokens.get(0).getToken());
					ASTClass.instrum("Expression Statement","132");
                    return optionalUser;
                }
		//		ASTClass.instrum("If Statement","127");
            }
			ASTClass.instrum("If Statement","125");
        }
		ASTClass.instrum("If Statement","123");

        return Optional.empty();
    }
    //==================================================================
    // END USER
    //==================================================================


    public AccountSummary findAccountSummaryByUser(User user, Integer month, Integer year) {
        if(month == null || year == null) {
            LocalDate now = LocalDate.now();
			ASTClass.instrum("Variable Declaration Statement","147");
            month = now.getMonthValue();
			ASTClass.instrum("Expression Statement","148");
            year = now.getYear();
			ASTClass.instrum("Expression Statement","149");
        }
		ASTClass.instrum("If Statement","146");
        LOGGER.debug("Find account summary {} {}-{}", user, month, year);
		ASTClass.instrum("Expression Statement","151");
        AccountSummary accountSummary = new AccountSummary();
		ASTClass.instrum("Variable Declaration Statement","152");
        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		ASTClass.instrum("Variable Declaration Statement","153");

        // no budgets, first time access
        if(budgets.isEmpty()) {
            LOGGER.debug("First time access budgets {} {}-{}", user, month, year);
			ASTClass.instrum("Expression Statement","157");
            initCategoriesAndBudgets(user, month, year);
			ASTClass.instrum("Expression Statement","158");
            budgets = budgetDAO.findBudgets(user, month, year, true);
			ASTClass.instrum("Expression Statement","159");
        }
		ASTClass.instrum("If Statement","156");
        Map<Category, List<Budget>> grouped = budgets
                .stream()
                .collect(Collectors.groupingBy(Budget::getCategory));
		ASTClass.instrum("Variable Declaration Statement","161");

        for(Map.Entry<Category, List<Budget>> entry: grouped.entrySet()) {
            Category category = entry.getKey();
			ASTClass.instrum("Variable Declaration Statement","166");
            double budget = entry.getValue().stream().mapToDouble(Budget::getProjected).sum();
			ASTClass.instrum("Variable Declaration Statement","167");
            double spent = entry.getValue().stream().mapToDouble(Budget::getActual).sum();
			ASTClass.instrum("Variable Declaration Statement","168");
            Group group = new Group(category.getId(), category.getName());
			ASTClass.instrum("Variable Declaration Statement","169");
            group.setType(category.getType());
			ASTClass.instrum("Expression Statement","170");
            group.setBudget(budget);
			ASTClass.instrum("Expression Statement","171");
            group.setSpent(spent);
			ASTClass.instrum("Expression Statement","172");
            group.setBudgets(entry.getValue());
			ASTClass.instrum("Expression Statement","173");
            accountSummary.getGroups().add(group);
			ASTClass.instrum("Expression Statement","174");
        }

        accountSummary.getGroups().sort(Comparator.comparing(Group::getId));
		ASTClass.instrum("Expression Statement","177");
        return accountSummary;
    }

    private void initCategoriesAndBudgets(User user, int month, int year) {
        Collection<Category> categories = categoryDAO.findCategories(user);
		ASTClass.instrum("Variable Declaration Statement","182");
        // no categories, first time access
        if(categories.isEmpty()) {
            LOGGER.debug("Create default categories and budgets {} {}-{}", user, month, year);
			ASTClass.instrum("Expression Statement","185");
            generateDefaultCategoriesAndBudgets(user, month, year);
			ASTClass.instrum("Expression Statement","186");
        } else {
            LOGGER.debug("Copy budgets {} {}-{}", user, month, year);
			ASTClass.instrum("Expression Statement","188");
            generateBudgets(user, month, year);
			ASTClass.instrum("Expression Statement","189");
        }
		ASTClass.instrum("If Statement","184");
    }

    public UsageSummary findUsageSummaryByUser(User user, Integer month, Integer year) {

        if(month == null || year == null) {
            LocalDate now = LocalDate.now();
			ASTClass.instrum("Variable Declaration Statement","196");
            month = now.getMonthValue();
			ASTClass.instrum("Expression Statement","197");
            year = now.getYear();
			ASTClass.instrum("Expression Statement","198");
        }
		ASTClass.instrum("If Statement","195");

        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		ASTClass.instrum("Variable Declaration Statement","201");

        double income =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.INCOME)
                        .mapToDouble(Budget::getActual)
                        .sum();
		ASTClass.instrum("Variable Declaration Statement","203");

        double budget =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.EXPENDITURE)
                        .mapToDouble(Budget::getProjected)
                        .sum();
		ASTClass.instrum("Variable Declaration Statement","210");

        double spent =
                budgets
                        .stream()
                        .filter(p -> p.getCategory().getType() == CategoryType.EXPENDITURE)
                        .mapToDouble(Budget::getActual)
                        .sum();
		ASTClass.instrum("Variable Declaration Statement","217");
        return new UsageSummary(income, budget, spent);
    }

    private void generateDefaultCategoriesAndBudgets(User user, int month, int year) {
        Collection<Category> categories = categoryDAO.addDefaultCategories(user);
		ASTClass.instrum("Variable Declaration Statement","227");
        Map<String, List<Budget>> defaultBudgets = budgetDAO.findDefaultBudgets();
		ASTClass.instrum("Variable Declaration Statement","228");
        Date period = Util.yearMonthDate(month, year);
		ASTClass.instrum("Variable Declaration Statement","229");
        for(Category category: categories) {
            List<Budget> budgets = defaultBudgets.get(category.getName());
			ASTClass.instrum("Variable Declaration Statement","231");
            if(budgets != null) {
                for(Budget budget : budgets) {
                    BudgetType budgetType = budgetTypeDAO.addBudgetType();
					ASTClass.instrum("Variable Declaration Statement","234");
                    Budget newBudget = new Budget();
					ASTClass.instrum("Variable Declaration Statement","235");
                    newBudget.setName(budget.getName());
					ASTClass.instrum("Expression Statement","236");
                    newBudget.setPeriod(period);
					ASTClass.instrum("Expression Statement","237");
                    newBudget.setCategory(category);
					ASTClass.instrum("Expression Statement","238");
                    newBudget.setBudgetType(budgetType);
					ASTClass.instrum("Expression Statement","239");
                    budgetDAO.addBudget(user, newBudget);
					ASTClass.instrum("Expression Statement","240");
                }
            }
			ASTClass.instrum("If Statement","232");
        }
    }

    //==================================================================
    // BUDGET
    //==================================================================

    public Budget addBudget(User user, AddBudgetForm budgetForm) {
        BudgetType budgetType = budgetTypeDAO.addBudgetType();
		ASTClass.instrum("Variable Declaration Statement","251");
        Budget budget = new Budget(budgetForm);
		ASTClass.instrum("Variable Declaration Statement","252");
        budget.setBudgetType(budgetType);
		ASTClass.instrum("Expression Statement","253");
        return budgetDAO.addBudget(user, budget);
    }

    public Budget updateBudget(User user, UpdateBudgetForm budgetForm) {
        Budget budget = budgetDAO.findById(user, budgetForm.getId());
		ASTClass.instrum("Variable Declaration Statement","258");
        Category category = categoryDAO.findById(budget.getCategory().getId());
		ASTClass.instrum("Variable Declaration Statement","259");
        budget.setName(budgetForm.getName());
		ASTClass.instrum("Expression Statement","260");
        budget.setProjected(budgetForm.getProjected());
		ASTClass.instrum("Expression Statement","261");
        // INCOME type allow user change actual without
        // add transactions
        if(category.getType() == CategoryType.INCOME) {
            budget.setActual(budgetForm.getActual());
			ASTClass.instrum("Expression Statement","265");
        }
		ASTClass.instrum("If Statement","264");
        budgetDAO.update(budget);
		ASTClass.instrum("Expression Statement","267");
        return budget;
    }

    public void deleteBudget(User user, long budgetId) {
        Budget budget = budgetDAO.findById(user, budgetId);
		ASTClass.instrum("Variable Declaration Statement","272");
        budgetDAO.delete(budget);
		ASTClass.instrum("Expression Statement","273");
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
		ASTClass.instrum("Variable Declaration Statement","293");
        // use current month's budgets
        // when user navigate backward
        List<Budget> originalBudgets = budgetDAO.findBudgets(user, now.getMonthValue(), now.getYear(), false);
		ASTClass.instrum("Variable Declaration Statement","296");
        // current month budget is empty
        if(originalBudgets.isEmpty()) {
            // use latest budget
            Date latestDate = budgetDAO.findLatestBudget(user);
			ASTClass.instrum("Variable Declaration Statement","300");
            LocalDate date = latestDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			ASTClass.instrum("Variable Declaration Statement","301");
            originalBudgets = budgetDAO.findBudgets(user, date.getMonthValue(), date.getYear(), false);
			ASTClass.instrum("Expression Statement","302");
        }
		ASTClass.instrum("If Statement","298");
        Date period = Util.yearMonthDate(month, year);
		ASTClass.instrum("Variable Declaration Statement","304");
        for(Budget budget : originalBudgets) {
            Budget newBudget = new Budget();
			ASTClass.instrum("Variable Declaration Statement","306");
            newBudget.setName(budget.getName());
			ASTClass.instrum("Expression Statement","307");
            newBudget.setProjected(budget.getProjected());
			ASTClass.instrum("Expression Statement","308");
            newBudget.setPeriod(period);
			ASTClass.instrum("Expression Statement","309");
            newBudget.setCategory(budget.getCategory());
			ASTClass.instrum("Expression Statement","310");
            newBudget.setBudgetType(budget.getBudgetType());
			ASTClass.instrum("Expression Statement","311");
            budgetDAO.addBudget(user, newBudget);
			ASTClass.instrum("Expression Statement","312");
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
		ASTClass.instrum("Variable Declaration Statement","328");
        if(!Util.inMonth(recurringForm.getRecurringAt(), new Date())) {
            throw new DataConstraintException("recurringAt", "Recurring Date must within " + Util.toFriendlyMonthDisplay(now) + " " + (now.getYear() + 1900));
        }
		ASTClass.instrum("If Statement","329");
        // end validation
        Budget budget = findBudgetById(user, recurringForm.getBudgetId());
		ASTClass.instrum("Variable Declaration Statement","333");
        budget.setActual(budget.getActual() + recurringForm.getAmount());
		ASTClass.instrum("Expression Statement","334");
        budgetDAO.update(budget);
		ASTClass.instrum("Expression Statement","335");

        Recurring recurring = new Recurring();
		ASTClass.instrum("Variable Declaration Statement","337");
        recurring.setAmount(recurringForm.getAmount());
		ASTClass.instrum("Expression Statement","338");
        recurring.setLastRunAt(recurringForm.getRecurringAt());
		ASTClass.instrum("Expression Statement","339");
        recurring.setRecurringType(recurringForm.getRecurringType());
		ASTClass.instrum("Expression Statement","340");
        recurring.setBudgetType(budget.getBudgetType());
		ASTClass.instrum("Expression Statement","341");
        recurring.setRemark(recurringForm.getRemark());
		ASTClass.instrum("Expression Statement","342");
        recurring = recurringDAO.addRecurring(recurring);
		ASTClass.instrum("Expression Statement","343");

        Transaction transaction = new Transaction();
		ASTClass.instrum("Variable Declaration Statement","345");
        transaction.setName(budget.getName());
		ASTClass.instrum("Expression Statement","346");
        transaction.setAmount(recurring.getAmount());
		ASTClass.instrum("Expression Statement","347");
        transaction.setRemark(recurringForm.getRemark());
		ASTClass.instrum("Expression Statement","348");
        transaction.setAuto(Boolean.TRUE);
		ASTClass.instrum("Expression Statement","349");
        transaction.setTransactionOn(recurring.getLastRunAt());
		ASTClass.instrum("Expression Statement","350");
        transaction.setBudget(budget);
		ASTClass.instrum("Expression Statement","351");
        transaction.setRecurring(recurring);
		ASTClass.instrum("Expression Statement","352");
        transactionDAO.addTransaction(transaction);
		ASTClass.instrum("Expression Statement","353");


        return recurring;
    }

    public List<Recurring> findRecurrings(User user) {
        List<Recurring> results = recurringDAO.findRecurrings(user);
		ASTClass.instrum("Variable Declaration Statement","360");
        // TODO: fix N + 1 query but now still OK
        results.forEach(this::populateRecurring);
		ASTClass.instrum("Expression Statement","362");
        LOGGER.debug("Found recurrings {}", results);
		ASTClass.instrum("Expression Statement","363");
        return results;
    }

    public void updateRecurrings() {
        LOGGER.debug("Begin update recurrings...");
		ASTClass.instrum("Expression Statement","368");
        List<Recurring> recurrings = recurringDAO.findActiveRecurrings();
		ASTClass.instrum("Variable Declaration Statement","369");
        LOGGER.debug("Found {} recurring(s) item to update", recurrings.size());
		ASTClass.instrum("Expression Statement","370");
        for (Recurring recurring : recurrings) {

            // budget
            Budget budget = budgetDAO.findByBudgetType(recurring.getBudgetType().getId());
			ASTClass.instrum("Variable Declaration Statement","374");
            budget.setActual(budget.getActual() + recurring.getAmount());
			ASTClass.instrum("Expression Statement","375");
            budgetDAO.update(budget);
            // end budget
			ASTClass.instrum("Expression Statement","376");

            // recurring
            recurring.setLastRunAt(new Date());
			ASTClass.instrum("Expression Statement","380");
            recurringDAO.update(recurring);
            // end recurring
			ASTClass.instrum("Expression Statement","381");

            // transaction
            Transaction transaction = new Transaction();
			ASTClass.instrum("Variable Declaration Statement","385");
            transaction.setName(budget.getName());
			ASTClass.instrum("Expression Statement","386");
            transaction.setAmount(recurring.getAmount());
			ASTClass.instrum("Expression Statement","387");
            transaction.setRecurring(recurring);
			ASTClass.instrum("Expression Statement","388");
            transaction.setRemark(recurring.getRecurringTypeDisplay() + " recurring for " + budget.getName());
			ASTClass.instrum("Expression Statement","389");
            transaction.setAuto(true);
			ASTClass.instrum("Expression Statement","390");
            transaction.setBudget(budget);
			ASTClass.instrum("Expression Statement","391");
            transaction.setTransactionOn(new Date());
			ASTClass.instrum("Expression Statement","392");
            transactionDAO.addTransaction(transaction);
            // end transaction
			ASTClass.instrum("Expression Statement","393");

        }
        LOGGER.debug("Finish update recurrings...");
		ASTClass.instrum("Expression Statement","397");
    }

    private void populateRecurring(Recurring recurring) {
        Budget budget = budgetDAO.findByBudgetType(recurring.getBudgetType().getId());
		ASTClass.instrum("Variable Declaration Statement","401");
        recurring.setName(budget.getName());
		ASTClass.instrum("Expression Statement","402");
    }

    public void deleteRecurring(User user, long recurringId) {
        Recurring recurring = recurringDAO.find(user, recurringId);
		ASTClass.instrum("Variable Declaration Statement","406");
        recurringDAO.delete(recurring);
		ASTClass.instrum("Expression Statement","407");
    }
    //==================================================================
    // END RECURRING
    //==================================================================


    //==================================================================
    // TRANSACTION
    //==================================================================
    public Transaction addTransaction(User user, TransactionForm transactionForm) {

        Budget budget = budgetDAO.findById(user, transactionForm.getBudget().getId());
		ASTClass.instrum("Variable Declaration Statement","419");

        // validation
        if(transactionForm.getAmount() == 0) {
            throw new DataConstraintException("amount", "Amount is required");
        }
		ASTClass.instrum("If Statement","422");

        if(Boolean.TRUE.equals(transactionForm.getRecurring()) && transactionForm.getRecurringType() == null) {
            throw new DataConstraintException("recurringType", "Recurring Type is required");
        }
		ASTClass.instrum("If Statement","426");

        Date transactionOn = transactionForm.getTransactionOn();
		ASTClass.instrum("Variable Declaration Statement","430");
        if(!Util.inMonth(transactionOn, budget.getPeriod())) {
            throw new DataConstraintException("transactionOn", "Transaction Date must within " + Util.toFriendlyMonthDisplay(budget.getPeriod()) + " " + (budget.getPeriod().getYear() + 1900));
        }
        // end validation
		ASTClass.instrum("If Statement","431");


        budget.setActual(budget.getActual() + transactionForm.getAmount());
		ASTClass.instrum("Expression Statement","437");
        budgetDAO.update(budget);
		ASTClass.instrum("Expression Statement","438");

        Recurring recurring = new Recurring();
		ASTClass.instrum("Variable Declaration Statement","440");
        if(Boolean.TRUE.equals(transactionForm.getRecurring())) {
            LOGGER.debug("Add recurring {} by {}", transactionForm, user);
			ASTClass.instrum("Expression Statement","442");
            recurring.setAmount(transactionForm.getAmount());
			ASTClass.instrum("Expression Statement","443");
            recurring.setRecurringType(transactionForm.getRecurringType());
			ASTClass.instrum("Expression Statement","444");
            recurring.setBudgetType(budget.getBudgetType());
			ASTClass.instrum("Expression Statement","445");
            recurring.setRemark(transactionForm.getRemark());
			ASTClass.instrum("Expression Statement","446");
            recurring.setLastRunAt(transactionForm.getTransactionOn());
			ASTClass.instrum("Expression Statement","447");
            recurringDAO.addRecurring(recurring);
			ASTClass.instrum("Expression Statement","448");
        }
		ASTClass.instrum("If Statement","441");

        Transaction transaction = new Transaction();
		ASTClass.instrum("Variable Declaration Statement","451");
        transaction.setName(budget.getName());
		ASTClass.instrum("Expression Statement","452");
        transaction.setAmount(transactionForm.getAmount());
		ASTClass.instrum("Expression Statement","453");
        transaction.setRemark(transactionForm.getRemark());
		ASTClass.instrum("Expression Statement","454");
        transaction.setAuto(Boolean.TRUE.equals(transactionForm.getRecurring()));
		ASTClass.instrum("Expression Statement","455");
        transaction.setTransactionOn(transactionForm.getTransactionOn());
		ASTClass.instrum("Expression Statement","456");
        transaction.setBudget(transactionForm.getBudget());
		ASTClass.instrum("Expression Statement","457");
        if(Boolean.TRUE.equals(transactionForm.getRecurring())) {
            transaction.setRecurring(recurring);
			ASTClass.instrum("Expression Statement","459");
        }
		ASTClass.instrum("If Statement","458");

        return transactionDAO.addTransaction(transaction);
    }

    public boolean deleteTransaction(User user, long transactionId) {
        // only delete transaction that belong to that user
        Optional<Transaction> optional = transactionDAO.findById(user, transactionId);
		ASTClass.instrum("Variable Declaration Statement","467");
        if(optional.isPresent()) {
            Transaction transaction = optional.get();
			ASTClass.instrum("Variable Declaration Statement","469");
            Budget budget = transaction.getBudget();
			ASTClass.instrum("Variable Declaration Statement","470");
            budget.setActual(budget.getActual() - transaction.getAmount());
			ASTClass.instrum("Expression Statement","471");
            transactionDAO.delete(transaction);
			ASTClass.instrum("Expression Statement","472");
            return true;
        }
		ASTClass.instrum("If Statement","468");
        return false;
    }

    public Transaction findTransactionById(long transactionId) {
        return transactionDAO.findById(transactionId);
    }

    public List<Transaction> findRecentTransactions(User user, Integer limit) {
        if(limit == null) {
            limit = 20;
			ASTClass.instrum("Expression Statement","484");
        }
		ASTClass.instrum("If Statement","483");
        return transactionDAO.find(user, limit);
    }

    public List<Transaction> findTodayRecurringsTransactions(User user) {
        SearchFilter filter = new SearchFilter();
		ASTClass.instrum("Variable Declaration Statement","490");
        filter.setStartOn(new Date());
		ASTClass.instrum("Expression Statement","491");
        filter.setEndOn(new Date());
		ASTClass.instrum("Expression Statement","492");
        filter.setAuto(Boolean.TRUE);
		ASTClass.instrum("Expression Statement","493");
        return transactionDAO.findTransactions(user, filter);
    }

    public List<Transaction> findTransactionsByRecurring(User user, long recurringId) {
        return transactionDAO.findByRecurring(user, recurringId);
    }

    public List<Transaction> findTransactions(User user, SearchFilter filter) {
        LOGGER.debug("Search transactions with {}", filter);
		ASTClass.instrum("Expression Statement","502");
        return transactionDAO.findTransactions(user, filter);
    }

    public List<Transaction> findTransactionsByBudget(User user, long budgetId) {
        return transactionDAO.findByBudget(user, budgetId);
    }

    public List<Point> findTransactionUsage(User user, Integer month, Integer year) {
        LocalDate now = LocalDate.now();
		ASTClass.instrum("Variable Declaration Statement","511");

        if(month == null || year == null) {
            month = now.getMonthValue();
			ASTClass.instrum("Expression Statement","514");
            year = now.getYear();
			ASTClass.instrum("Expression Statement","515");
        }
		ASTClass.instrum("If Statement","513");

        List<Point> points = new ArrayList<>();
		ASTClass.instrum("Variable Declaration Statement","518");

        LocalDate begin = LocalDate.of(year, month, 1);
		ASTClass.instrum("Variable Declaration Statement","520");

        LocalDate ending = LocalDate.of(year, month, begin.lengthOfMonth());
		ASTClass.instrum("Variable Declaration Statement","522");
        if(now.getMonthValue() == month && now.getYear() == year) {
            ending = now;
			ASTClass.instrum("Expression Statement","524");
        }
		ASTClass.instrum("If Statement","523");

        // first 1-7 days show last 7 days's transactions instead of
        // show 1 or 2 days
        if(ending.getDayOfMonth() < 7) {
            begin = begin.minusDays(7 - ending.getDayOfMonth());
			ASTClass.instrum("Expression Statement","530");
        }
		ASTClass.instrum("If Statement","529");

        Instant instantStart = begin.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		ASTClass.instrum("Variable Declaration Statement","533");
        Date start = Date.from(instantStart);
		ASTClass.instrum("Variable Declaration Statement","534");

        Instant instantEnd = ending.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		ASTClass.instrum("Variable Declaration Statement","536");
        Date end = Date.from(instantEnd);
		ASTClass.instrum("Variable Declaration Statement","537");


        List<Transaction> transactions = transactionDAO.findByRange(user, start, end);
		ASTClass.instrum("Variable Declaration Statement","540");

        Map<Date, List<Transaction>> groups = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionOn, TreeMap::new, Collectors.toList()));
		ASTClass.instrum("Variable Declaration Statement","542");

        int days = Period.between(begin, ending).getDays() + 1;
		ASTClass.instrum("Variable Declaration Statement","546");

        for (int i = 0; i < days; i++) {
            LocalDate day = begin.plusDays(i);
			ASTClass.instrum("Variable Declaration Statement","549");
            Instant instantDay = day.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			ASTClass.instrum("Variable Declaration Statement","550");
            Date dayDate = Date.from(instantDay);
			ASTClass.instrum("Variable Declaration Statement","551");
            groups.putIfAbsent(dayDate, Collections.emptyList());
			ASTClass.instrum("Expression Statement","552");
        }
		ASTClass.instrum("For Statement","548");

        for (Map.Entry<Date, List<Transaction>> entry : groups.entrySet()) {
            double total = entry.getValue()
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
			ASTClass.instrum("Variable Declaration Statement","556");

            LocalDate res = Util.toLocalDate(entry.getKey());
			ASTClass.instrum("Variable Declaration Statement","561");
            Point point = new Point(SUMMARY_DATE_FORMATTER.format(res), entry.getKey().getTime(), total, PointType.TRANSACTIONS);
			ASTClass.instrum("Variable Declaration Statement","562");
            points.add(point);
			ASTClass.instrum("Expression Statement","563");
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
			ASTClass.instrum("Variable Declaration Statement","591");
            month = now.getMonthValue();
			ASTClass.instrum("Expression Statement","592");
            year = now.getYear();
			ASTClass.instrum("Expression Statement","593");
        }
		ASTClass.instrum("If Statement","590");

        List<Point> points = new ArrayList<>();
		ASTClass.instrum("Variable Declaration Statement","596");
        List<Budget> budgets = budgetDAO.findBudgets(user, month, year, true);
		ASTClass.instrum("Variable Declaration Statement","597");
        Map<Category, List<Budget>> groups = budgets
                .stream()
                .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                .collect(Collectors.groupingBy(Budget::getCategory));
		ASTClass.instrum("Variable Declaration Statement","598");
        for (Map.Entry<Category, List<Budget>> entry : groups.entrySet()) {
            double total = entry.getValue()
                    .stream()
                    .mapToDouble(Budget::getActual)
                    .sum();
			ASTClass.instrum("Variable Declaration Statement","603");
            Point point = new Point(entry.getKey().getName(), entry.getKey().getId(), total, PointType.CATEGORY);
			ASTClass.instrum("Variable Declaration Statement","607");
            points.add(point);
			ASTClass.instrum("Expression Statement","608");
        }

        points.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		ASTClass.instrum("Expression Statement","611");
        return points;
    }

    public List<Point> findMonthlyTransactionUsage(User user) {
        List<Point> points = new ArrayList<>();
		ASTClass.instrum("Variable Declaration Statement","616");
        LocalDate end = LocalDate.now();
		ASTClass.instrum("Variable Declaration Statement","617");
        LocalDate start = end.minusMonths(6);
		ASTClass.instrum("Variable Declaration Statement","618");
        List<Budget> budgets = budgetDAO.findByRange(user, start.getMonthValue(), start.getYear(), end.getMonthValue(), end.getYear());
		ASTClass.instrum("Variable Declaration Statement","619");

        // group by period
        Map<Date, List<Budget>> groups = budgets
                .stream()
                .collect(Collectors.groupingBy(Budget::getPeriod, TreeMap::new, Collectors.toList()));
		ASTClass.instrum("Variable Declaration Statement","622");

        LocalDate now = LocalDate.now();
		ASTClass.instrum("Variable Declaration Statement","626");
        // populate empty months, if any
        for (int i = 0; i < 6; i++) {
            LocalDate day = now.minusMonths(i).withDayOfMonth(1);
			ASTClass.instrum("Variable Declaration Statement","629");
            groups.putIfAbsent(Util.toDate(day), Collections.emptyList());
			ASTClass.instrum("Expression Statement","630");
        }
		ASTClass.instrum("For Statement","628");

        // generate points
        for (Map.Entry<Date, List<Budget>> entry : groups.entrySet()) {
            // budget
            double budget = entry.getValue()
                    .stream()
                    .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                    .mapToDouble(Budget::getProjected)
                    .sum();
			ASTClass.instrum("Variable Declaration Statement","636");

            // spending
            double spending = entry.getValue()
                    .stream()
                    .filter(b -> b.getActual() > 0)
                    .filter(b -> b.getCategory().getType() == CategoryType.EXPENDITURE)
                    .mapToDouble(Budget::getActual)
                    .sum();
			ASTClass.instrum("Variable Declaration Statement","643");

            // refund
            double refund = entry.getValue()
                    .stream()
                    .filter(b -> b.getActual() < 0)
                    .mapToDouble(Budget::getActual)
                    .sum();
			ASTClass.instrum("Variable Declaration Statement","651");

            String month = Util.toFriendlyMonthDisplay(entry.getKey());
			ASTClass.instrum("Variable Declaration Statement","657");
            Point spendingPoint = new Point(month, entry.getKey().getTime(), spending, PointType.MONTHLY_SPEND);
			ASTClass.instrum("Variable Declaration Statement","658");
            Point refundPoint = new Point(month, entry.getKey().getTime(), refund, PointType.MONTHLY_REFUND);
			ASTClass.instrum("Variable Declaration Statement","659");
            Point budgetPoint = new Point(month, entry.getKey().getTime(), budget, PointType.MONTHLY_BUDGET);
			ASTClass.instrum("Variable Declaration Statement","660");

            points.add(spendingPoint);
			ASTClass.instrum("Expression Statement","662");
            points.add(refundPoint);
			ASTClass.instrum("Expression Statement","663");
            points.add(budgetPoint);
			ASTClass.instrum("Expression Statement","664");
        }
        return points;
    }

    public void deleteCategory(User user, long categoryId) {
        Category category = categoryDAO.find(user, categoryId);
		ASTClass.instrum("Variable Declaration Statement","670");
        categoryDAO.delete(category);
		ASTClass.instrum("Expression Statement","671");
    }

    public List<String> findCategorySuggestions(User user, String q) {
        return categoryDAO.findSuggestions(user, q);
    }

    //==================================================================
    // END CATEGORY
    //==================================================================
}
