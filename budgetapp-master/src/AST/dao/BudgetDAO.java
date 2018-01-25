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
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","37");
    }

    /**
     * add budget for a given user
     * @param user owner
     * @param budget new budget
     * @return new budget
     */
    public Budget addBudget(User user, Budget budget) {
        LOGGER.debug("User {} add budget {}", user, budget);
		//ASTClass.instrum("Expression Statement","50");
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","48");
		//ASTClass.instrum("Expression Statement","47");
        if(budget.getPeriod() == null) {
            budget.setPeriod(Util.currentYearMonth());
			//ASTClass.instrum("Expression Statement","49");
			//ASTClass.instrum("Expression Statement","51");
			//ASTClass.instrum("Expression Statement","53");
			//ASTClass.instrum("Expression Statement","55");
        }
		//ASTClass.instrum("If Statement","54");
		//ASTClass.instrum("If Statement","52");
		//ASTClass.instrum("If Statement","50");
		//ASTClass.instrum("If Statement","48");
        budget.setUser(user);
		//ASTClass.instrum("Expression Statement","63");
		//ASTClass.instrum("Expression Statement","59");
		//ASTClass.instrum("Expression Statement","55");
		//ASTClass.instrum("Expression Statement","51");
        return persist(budget);
    }

    /**
     * find budgets for a given user for current month-year
     * @param user
     * @return
     */
    public List<Budget> findBudgets(User user) {
        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","76");
		//ASTClass.instrum("Variable Declaration Statement","71");
		//ASTClass.instrum("Variable Declaration Statement","66");
		//ASTClass.instrum("Variable Declaration Statement","61");
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
		//ASTClass.instrum("Expression Statement","92");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","80");
		//ASTClass.instrum("Expression Statement","74");
        Date yearMonth = Util.yearMonthDate(month, year);
		//ASTClass.instrum("Variable Declaration Statement","96");
		//ASTClass.instrum("Variable Declaration Statement","89");
		//ASTClass.instrum("Variable Declaration Statement","82");
		//ASTClass.instrum("Variable Declaration Statement","75");
        Criteria criteria = criteria();
		//ASTClass.instrum("Variable Declaration Statement","100");
		//ASTClass.instrum("Variable Declaration Statement","92");
		//ASTClass.instrum("Variable Declaration Statement","84");
		//ASTClass.instrum("Variable Declaration Statement","76");
        if(!lazy) {
            criteria.setFetchMode("category", FetchMode.JOIN);
			//ASTClass.instrum("Expression Statement","78");
			//ASTClass.instrum("Expression Statement","87");
			//ASTClass.instrum("Expression Statement","96");
			//ASTClass.instrum("Expression Statement","105");
        }
		//ASTClass.instrum("If Statement","104");
		//ASTClass.instrum("If Statement","95");
		//ASTClass.instrum("If Statement","86");
		//ASTClass.instrum("If Statement","77");
        criteria.add(Restrictions.eq("user", user));
		//ASTClass.instrum("Expression Statement","113");
		//ASTClass.instrum("Expression Statement","102");
		//ASTClass.instrum("Expression Statement","91");
		//ASTClass.instrum("Expression Statement","80");
        criteria.add(Restrictions.eq("period", yearMonth));
		//ASTClass.instrum("Expression Statement","117");
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","93");
		//ASTClass.instrum("Expression Statement","81");
        criteria.addOrder(Order.asc("id"));
		//ASTClass.instrum("Expression Statement","121");
		//ASTClass.instrum("Expression Statement","108");
		//ASTClass.instrum("Expression Statement","95");
		//ASTClass.instrum("Expression Statement","82");
        return list(criteria);
    }

    public Date findLatestBudget(User user) {
        LOGGER.debug("Find latest budget by user {}", user);
		//ASTClass.instrum("Expression Statement","129");
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","101");
		//ASTClass.instrum("Expression Statement","87");
        Criteria criteria = criteria();
		//ASTClass.instrum("Variable Declaration Statement","133");
		//ASTClass.instrum("Variable Declaration Statement","118");
		//ASTClass.instrum("Variable Declaration Statement","103");
		//ASTClass.instrum("Variable Declaration Statement","88");
        criteria.add(Restrictions.eq("user", user));
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","121");
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","89");
        criteria.setProjection(Projections.max("createdAt"));
		//ASTClass.instrum("Expression Statement","141");
		//ASTClass.instrum("Expression Statement","124");
		//ASTClass.instrum("Expression Statement","107");
		//ASTClass.instrum("Expression Statement","90");
        criteria.setMaxResults(1);
		//ASTClass.instrum("Expression Statement","145");
		//ASTClass.instrum("Expression Statement","127");
		//ASTClass.instrum("Expression Statement","109");
		//ASTClass.instrum("Expression Statement","91");
        return (Date)criteria.uniqueResult();
    }

    /**
     * find budget by given id
     * @param budgetId
     * @return
     */
    public Budget findById(long budgetId) {
        Budget budget = get(budgetId);
		//ASTClass.instrum("Variable Declaration Statement","101");
		//ASTClass.instrum("Variable Declaration Statement","120");
		//ASTClass.instrum("Variable Declaration Statement","139");
		//ASTClass.instrum("Variable Declaration Statement","158");

        if(budget == null) {
            throw new NotFoundException();
        }
		//ASTClass.instrum("If Statement","163");
		//ASTClass.instrum("If Statement","143");
		//ASTClass.instrum("If Statement","123");
		//ASTClass.instrum("If Statement","103");
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
		//ASTClass.instrum("Variable Declaration Statement","179");
		//ASTClass.instrum("Variable Declaration Statement","158");
		//ASTClass.instrum("Variable Declaration Statement","137");
		//ASTClass.instrum("Variable Declaration Statement","116");
        if(!Objects.equals(user.getId(), budget.getUser().getId())) {
            throw new AccessDeniedException();
        }
		//ASTClass.instrum("If Statement","183");
		//ASTClass.instrum("If Statement","161");
		//ASTClass.instrum("If Statement","139");
		//ASTClass.instrum("If Statement","117");
        return budget;
    }

    public void update(Budget budget) {
        persist(budget);
		//ASTClass.instrum("Expression Statement","124");
		//ASTClass.instrum("Expression Statement","147");
		//ASTClass.instrum("Expression Statement","170");
		//ASTClass.instrum("Expression Statement","193");
    }

    public void delete(Budget budget) {
        currentSession().delete(budget);
		//ASTClass.instrum("Expression Statement","128");
		//ASTClass.instrum("Expression Statement","152");
		//ASTClass.instrum("Expression Statement","176");
		//ASTClass.instrum("Expression Statement","200");
    }
    public Map<String, List<Budget>> findDefaultBudgets() {
        return configuration.getBudgets();
    }

    public List<Budget> findByRange(User user, int startMonth, int startYear, int endMonth, int endYear) {
        Date start = Util.yearMonthDate(startMonth, startYear);
		//ASTClass.instrum("Variable Declaration Statement","210");
		//ASTClass.instrum("Variable Declaration Statement","185");
		//ASTClass.instrum("Variable Declaration Statement","160");
		//ASTClass.instrum("Variable Declaration Statement","135");
        Date end = Util.yearMonthDate(endMonth, endYear);
		//ASTClass.instrum("Variable Declaration Statement","214");
		//ASTClass.instrum("Variable Declaration Statement","188");
		//ASTClass.instrum("Variable Declaration Statement","162");
		//ASTClass.instrum("Variable Declaration Statement","136");
        Query<Budget> query = query("FROM Budget b WHERE b.user = :user AND b.period BETWEEN :start AND :end");
		//ASTClass.instrum("Variable Declaration Statement","218");
		//ASTClass.instrum("Variable Declaration Statement","191");
		//ASTClass.instrum("Variable Declaration Statement","164");
		//ASTClass.instrum("Variable Declaration Statement","137");
        query
                .setParameter("user", user)
                .setParameter("start", start)
                .setParameter("end", end);
		//ASTClass.instrum("Expression Statement","138");
		//ASTClass.instrum("Expression Statement","166");
		//ASTClass.instrum("Expression Statement","194");
		//ASTClass.instrum("Expression Statement","222");

        return list(query);
    }

    public Budget findByBudgetType(Long budgetTypeId) {
        Date now = Util.currentYearMonth();
		//ASTClass.instrum("Variable Declaration Statement","234");
		//ASTClass.instrum("Variable Declaration Statement","205");
		//ASTClass.instrum("Variable Declaration Statement","176");
		//ASTClass.instrum("Variable Declaration Statement","147");
        Query<Budget> query = query("FROM Budget b WHERE b.budgetType.id = :budgetTypeId AND b.period = :period");
		//ASTClass.instrum("Variable Declaration Statement","238");
		//ASTClass.instrum("Variable Declaration Statement","208");
		//ASTClass.instrum("Variable Declaration Statement","178");
		//ASTClass.instrum("Variable Declaration Statement","148");
        query
                .setParameter("budgetTypeId", budgetTypeId)
                .setParameter("period", now);
		//ASTClass.instrum("Expression Statement","242");
		//ASTClass.instrum("Expression Statement","211");
		//ASTClass.instrum("Expression Statement","180");
		//ASTClass.instrum("Expression Statement","149");
        return uniqueResult(query);
    }

    public List<Budget> findByUserAndCategory(User user, long categoryId) {
        Criteria criteria = userCriteria(user);
		//ASTClass.instrum("Variable Declaration Statement","252");
		//ASTClass.instrum("Variable Declaration Statement","220");
		//ASTClass.instrum("Variable Declaration Statement","188");
		//ASTClass.instrum("Variable Declaration Statement","156");
        criteria.add(Restrictions.eq("category.id", categoryId));
		//ASTClass.instrum("Expression Statement","256");
		//ASTClass.instrum("Expression Statement","223");
		//ASTClass.instrum("Expression Statement","190");
		//ASTClass.instrum("Expression Statement","157");
        criteria.add(Restrictions.eq("period", Util.currentYearMonth()));
		//ASTClass.instrum("Expression Statement","260");
		//ASTClass.instrum("Expression Statement","226");
		//ASTClass.instrum("Expression Statement","192");
		//ASTClass.instrum("Expression Statement","158");
        return list(criteria);
    }

    public List<String> findSuggestions(User user, String q) {
        q = q == null? "": q.toLowerCase();
		//ASTClass.instrum("Expression Statement","163");
		//ASTClass.instrum("Expression Statement","198");
		//ASTClass.instrum("Expression Statement","233");
		//ASTClass.instrum("Expression Statement","268");

        Query<String> query = currentSession().createQuery("SELECT b.name FROM Budget l WHERE b.user != :user AND LOWER(b.name) LIKE :q", String.class);
		//ASTClass.instrum("Variable Declaration Statement","273");
		//ASTClass.instrum("Variable Declaration Statement","237");
		//ASTClass.instrum("Variable Declaration Statement","201");
		//ASTClass.instrum("Variable Declaration Statement","165");
        query
                .setParameter("user", user)
                .setParameter("q", "%" + q + "%");
		//ASTClass.instrum("Expression Statement","166");
		//ASTClass.instrum("Expression Statement","203");
		//ASTClass.instrum("Expression Statement","240");
		//ASTClass.instrum("Expression Statement","277");

        return query.list();
    }

    private Criteria defaultCriteria() {
        return criteria();
    }

    private Criteria userCriteria(User user) {
        Criteria criteria = defaultCriteria();
		//ASTClass.instrum("Variable Declaration Statement","292");
		//ASTClass.instrum("Variable Declaration Statement","254");
		//ASTClass.instrum("Variable Declaration Statement","216");
		//ASTClass.instrum("Variable Declaration Statement","178");
        criteria.add(Restrictions.eq("user", user));
		//ASTClass.instrum("Expression Statement","296");
		//ASTClass.instrum("Expression Statement","257");
		//ASTClass.instrum("Expression Statement","218");
		//ASTClass.instrum("Expression Statement","179");
        return criteria;
    }

}
