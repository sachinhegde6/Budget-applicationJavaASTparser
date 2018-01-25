package io.budgetapp.dao;

import io.budgetapp.model.Budget;
import io.budgetapp.util.Util;
import io.budgetapp.application.AccessDeniedException;
import io.budgetapp.application.NotFoundException;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.model.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class BudgetDAO extends AbstractDAO<Budget> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetDAO.class);

    private final AppConfiguration configuration;

    public BudgetDAO(SessionFactory sessionFactory, AppConfiguration configuration) {
        super(sessionFactory);
        this.configuration = configuration;
		ASTClass.instrum("Expression Statement","37");
    }

    /**
     * add budget for a given user
     * @param user owner
     * @param budget new budget
     * @return new budget
     */
    public Budget addBudget(User user, Budget budget) {
        LOGGER.debug("User {} add budget {}", user, budget);
		ASTClass.instrum("Expression Statement","47");
        if(budget.getPeriod() == null) {
            budget.setPeriod(Util.currentYearMonth());
			ASTClass.instrum("Expression Statement","49");
        }
		ASTClass.instrum("If Statement","48");
        budget.setUser(user);
		ASTClass.instrum("Expression Statement","51");
        return persist(budget);
    }

    /**
     * find budgets for a given user for current month-year
     * @param user
     * @return
     */
    public List<Budget> findBudgets(User user) {
        LocalDate now = LocalDate.now();
		ASTClass.instrum("Variable Declaration Statement","61");
        return findBudgets(user, now.getMonthValue(), now.getYear(), false);
    }

    /**
     * find budgets for a given user for given month-year
     * @param user
     * @param month
     * @param year
     * @param lazy
     * @return
     */
    public List<Budget> findBudgets(User user, int month, int year, boolean lazy) {
        LOGGER.debug("Find budgets by user {} by date {}-{}", user, month, year);
		ASTClass.instrum("Expression Statement","74");
        Date yearMonth = Util.yearMonthDate(month, year);
		ASTClass.instrum("Variable Declaration Statement","75");
        Criteria criteria = criteria();
		ASTClass.instrum("Variable Declaration Statement","76");
        if(!lazy) {
            criteria.setFetchMode("category", FetchMode.JOIN);
			ASTClass.instrum("Expression Statement","78");
        }
		ASTClass.instrum("If Statement","77");
        criteria.add(Restrictions.eq("user", user));
		ASTClass.instrum("Expression Statement","80");
        criteria.add(Restrictions.eq("period", yearMonth));
		ASTClass.instrum("Expression Statement","81");
        criteria.addOrder(Order.asc("id"));
		ASTClass.instrum("Expression Statement","82");
        return list(criteria);
    }

    public Date findLatestBudget(User user) {
        LOGGER.debug("Find latest budget by user {}", user);
		ASTClass.instrum("Expression Statement","87");
        Criteria criteria = criteria();
		ASTClass.instrum("Variable Declaration Statement","88");
        criteria.add(Restrictions.eq("user", user));
		ASTClass.instrum("Expression Statement","89");
        criteria.setProjection(Projections.max("createdAt"));
		ASTClass.instrum("Expression Statement","90");
        criteria.setMaxResults(1);
		ASTClass.instrum("Expression Statement","91");
        return (Date)criteria.uniqueResult();
    }

    /**
     * find budget by given id
     * @param budgetId
     * @return
     */
    public Budget findById(long budgetId) {
        Budget budget = get(budgetId);
		ASTClass.instrum("Variable Declaration Statement","101");

        if(budget == null) {
            throw new NotFoundException();
        }
		ASTClass.instrum("If Statement","103");
        return budget;
    }

    /**
     * find budget for given user and id
     * @param user
     * @param budgetId
     * @return
     */
    public Budget findById(User user, Long budgetId) {
        Budget budget = findById(budgetId);
		ASTClass.instrum("Variable Declaration Statement","116");
        if(!Objects.equals(user.getId(), budget.getUser().getId())) {
            throw new AccessDeniedException();
        }
		ASTClass.instrum("If Statement","117");
        return budget;
    }

    public void update(Budget budget) {
        persist(budget);
		ASTClass.instrum("Expression Statement","124");
    }

    public void delete(Budget budget) {
        currentSession().delete(budget);
		ASTClass.instrum("Expression Statement","128");
    }
    public Map<String, List<Budget>> findDefaultBudgets() {
        return configuration.getBudgets();
    }

    public List<Budget> findByRange(User user, int startMonth, int startYear, int endMonth, int endYear) {
        Date start = Util.yearMonthDate(startMonth, startYear);
		ASTClass.instrum("Variable Declaration Statement","135");
        Date end = Util.yearMonthDate(endMonth, endYear);
		ASTClass.instrum("Variable Declaration Statement","136");
        Query<Budget> query = query("FROM Budget b WHERE b.user = :user AND b.period BETWEEN :start AND :end");
		ASTClass.instrum("Variable Declaration Statement","137");
        query
                .setParameter("user", user)
                .setParameter("start", start)
                .setParameter("end", end);
		ASTClass.instrum("Expression Statement","138");

        return list(query);
    }

    public Budget findByBudgetType(Long budgetTypeId) {
        Date now = Util.currentYearMonth();
		ASTClass.instrum("Variable Declaration Statement","147");
        Query<Budget> query = query("FROM Budget b WHERE b.budgetType.id = :budgetTypeId AND b.period = :period");
		ASTClass.instrum("Variable Declaration Statement","148");
        query
                .setParameter("budgetTypeId", budgetTypeId)
                .setParameter("period", now);
		ASTClass.instrum("Expression Statement","149");
        return uniqueResult(query);
    }

    public List<Budget> findByUserAndCategory(User user, long categoryId) {
        Criteria criteria = userCriteria(user);
		ASTClass.instrum("Variable Declaration Statement","156");
        criteria.add(Restrictions.eq("category.id", categoryId));
		ASTClass.instrum("Expression Statement","157");
        criteria.add(Restrictions.eq("period", Util.currentYearMonth()));
		ASTClass.instrum("Expression Statement","158");
        return list(criteria);
    }

    public List<String> findSuggestions(User user, String q) {
        q = q == null? "": q.toLowerCase();
		ASTClass.instrum("Expression Statement","163");

        Query<String> query = currentSession().createQuery("SELECT b.name FROM Budget l WHERE b.user != :user AND LOWER(b.name) LIKE :q", String.class);
		ASTClass.instrum("Variable Declaration Statement","165");
        query
                .setParameter("user", user)
                .setParameter("q", "%" + q + "%");
		ASTClass.instrum("Expression Statement","166");

        return query.list();
    }

    private Criteria defaultCriteria() {
        return criteria();
    }

    private Criteria userCriteria(User user) {
        Criteria criteria = defaultCriteria();
		ASTClass.instrum("Variable Declaration Statement","178");
        criteria.add(Restrictions.eq("user", user));
		ASTClass.instrum("Expression Statement","179");
        return criteria;
    }

}
