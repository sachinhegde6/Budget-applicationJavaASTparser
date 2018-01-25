package io.budgetapp.dao;

import io.budgetapp.ASTClass;
import io.budgetapp.application.NotFoundException;
import io.budgetapp.model.Transaction;
import io.budgetapp.model.User;
import io.budgetapp.model.form.report.SearchFilter;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class TransactionDAO extends AbstractDAO<Transaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDAO.class);

    public TransactionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Transaction addTransaction(Transaction transaction) {
        LOGGER.debug("Add transaction {}", transaction);
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
        return persist(transaction);
    }

    public List<Transaction> find(User user, Integer limit) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user ORDER BY t.transactionOn DESC, t.id ASC");
		//ASTClass.instrum("Variable Declaration Statement","41");
		//ASTClass.instrum("Variable Declaration Statement","40");
		//ASTClass.instrum("Variable Declaration Statement","39");
		//ASTClass.instrum("Variable Declaration Statement","38");
        query.setParameter("user", user);
		//ASTClass.instrum("Expression Statement","45");
		//ASTClass.instrum("Expression Statement","43");
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","39");
        query.setMaxResults(limit);
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","43");
		//ASTClass.instrum("Expression Statement","40");
        return list(query);
    }

    public Transaction findById(long id) {
        Transaction transaction = get(id);
		//ASTClass.instrum("Variable Declaration Statement","57");
		//ASTClass.instrum("Variable Declaration Statement","53");
		//ASTClass.instrum("Variable Declaration Statement","49");
		//ASTClass.instrum("Variable Declaration Statement","45");
        if(transaction == null) {
            throw new NotFoundException();
        }
		//ASTClass.instrum("If Statement","46");
		//ASTClass.instrum("If Statement","51");
		//ASTClass.instrum("If Statement","56");
		//ASTClass.instrum("If Statement","61");

        return transaction;
    }

    public Optional<Transaction> findById(User user, long id) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.id = :id AND t.budget.user = :user");
		//ASTClass.instrum("Variable Declaration Statement","72");
		//ASTClass.instrum("Variable Declaration Statement","66");
		//ASTClass.instrum("Variable Declaration Statement","60");
		//ASTClass.instrum("Variable Declaration Statement","54");
        query.setParameter("user", user);
		//ASTClass.instrum("Expression Statement","76");
		//ASTClass.instrum("Expression Statement","69");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","55");
        query.setParameter("id", id);
		//ASTClass.instrum("Expression Statement","80");
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","56");
        Transaction result = query.uniqueResult();
		//ASTClass.instrum("Variable Declaration Statement","84");
		//ASTClass.instrum("Variable Declaration Statement","75");
		//ASTClass.instrum("Variable Declaration Statement","66");
		//ASTClass.instrum("Variable Declaration Statement","57");
        return Optional.ofNullable(result);
    }

    public List<Transaction> findByBudget(User user, long budgetId) {
        Criteria criteria = defaultCriteria();
		//ASTClass.instrum("Variable Declaration Statement","92");
		//ASTClass.instrum("Variable Declaration Statement","82");
		//ASTClass.instrum("Variable Declaration Statement","72");
		//ASTClass.instrum("Variable Declaration Statement","62");
        criteria.createAlias("t.budget", "budget");
		//ASTClass.instrum("Expression Statement","63");
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","85");
		//ASTClass.instrum("Expression Statement","96");

        criteria.add(Restrictions.eq("budget.id", budgetId));
		//ASTClass.instrum("Expression Statement","101");
		//ASTClass.instrum("Expression Statement","89");
		//ASTClass.instrum("Expression Statement","77");
		//ASTClass.instrum("Expression Statement","65");
        criteria.add(Restrictions.eq("budget.user", user));
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","92");
		//ASTClass.instrum("Expression Statement","79");
		//ASTClass.instrum("Expression Statement","66");
        return list(criteria);
    }

    public List<Transaction> findByRecurring(User user, long recurringId) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user AND t.recurring.id = :recurringId ORDER BY t.transactionOn DESC, t.id ASC");
		//ASTClass.instrum("Variable Declaration Statement","113");
		//ASTClass.instrum("Variable Declaration Statement","99");
		//ASTClass.instrum("Variable Declaration Statement","85");
		//ASTClass.instrum("Variable Declaration Statement","71");
        query.setParameter("user", user);
		//ASTClass.instrum("Expression Statement","117");
		//ASTClass.instrum("Expression Statement","102");
		//ASTClass.instrum("Expression Statement","87");
		//ASTClass.instrum("Expression Statement","72");
        query.setParameter("recurringId", recurringId);
		//ASTClass.instrum("Expression Statement","121");
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","89");
		//ASTClass.instrum("Expression Statement","73");
        return list(query);
    }

    public List<Transaction> findByRange(User user, Date start, Date end) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user AND t.transactionOn BETWEEN :start AND :end ORDER BY t.transactionOn DESC, t.id ASC");
		//ASTClass.instrum("Variable Declaration Statement","129");
		//ASTClass.instrum("Variable Declaration Statement","112");
		//ASTClass.instrum("Variable Declaration Statement","95");
		//ASTClass.instrum("Variable Declaration Statement","78");
        query
                .setParameter("user", user)
                .setParameter("start", start)
                .setParameter("end", end);
		//ASTClass.instrum("Expression Statement","79");
		//ASTClass.instrum("Expression Statement","97");
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","133");

        return list(query);
    }

    public List<Transaction> findTransactions(User user, SearchFilter filter) {
        Criteria criteria = defaultCriteria();
		//ASTClass.instrum("Variable Declaration Statement","145");
		//ASTClass.instrum("Variable Declaration Statement","126");
		//ASTClass.instrum("Variable Declaration Statement","107");
		//ASTClass.instrum("Variable Declaration Statement","88");
        criteria.createAlias("t.budget", "budget");
		//ASTClass.instrum("Expression Statement","89");
		//ASTClass.instrum("Expression Statement","109");
		//ASTClass.instrum("Expression Statement","129");
		//ASTClass.instrum("Expression Statement","149");

        criteria.add(Restrictions.eq("budget.user", user));
		//ASTClass.instrum("Expression Statement","91");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","133");
		//ASTClass.instrum("Expression Statement","154");

        if(filter.isAmountRange()) {
            criteria.add(Restrictions.between("amount", filter.getMinAmount(), filter.getMaxAmount()));
			//ASTClass.instrum("Expression Statement","94");
			//ASTClass.instrum("Expression Statement","116");
			//ASTClass.instrum("Expression Statement","138");
			//ASTClass.instrum("Expression Statement","160");
        } else {
			if(filter.getMinAmount() != null) {
            criteria.add(Restrictions.ge("amount", filter.getMinAmount()));
			//ASTClass.instrum("Expression Statement","97");
			//ASTClass.instrum("Expression Statement","120");
			//ASTClass.instrum("Expression Statement","143");
			//ASTClass.instrum("Expression Statement","166");
			} else {
                if (filter.getMaxAmount() != null) {
                    criteria.add(Restrictions.le("amount", filter.getMaxAmount()));
					//ASTClass.instrum("Expression Statement","100");
					//ASTClass.instrum("Expression Statement","124");
					//ASTClass.instrum("Expression Statement","148");
					//ASTClass.instrum("Expression Statement","172");
                }
				//ASTClass.instrum("If Statement","99");
				//ASTClass.instrum("If Statement","123");
				//ASTClass.instrum("If Statement","147");
				//ASTClass.instrum("If Statement","171");
        }
			//ASTClass.instrum("If Statement","96");
			//ASTClass.instrum("If Statement","119");
			//ASTClass.instrum("If Statement","142");
			//ASTClass.instrum("If Statement","165");
		}
		//ASTClass.instrum("If Statement","93");
		//ASTClass.instrum("If Statement","115");
		//ASTClass.instrum("If Statement","137");
		//ASTClass.instrum("If Statement","159");

        if(filter.isDateRange()) {
            criteria.add(Restrictions.between("transactionOn", filter.getStartOn(), filter.getEndOn()));
			//ASTClass.instrum("Expression Statement","106");
			//ASTClass.instrum("Expression Statement","134");
			//ASTClass.instrum("Expression Statement","162");
			//ASTClass.instrum("Expression Statement","190");
        } else {
			if(filter.getStartOn() != null) {
            criteria.add(Restrictions.ge("transactionOn", filter.getStartOn()));
			//ASTClass.instrum("Expression Statement","109");
			//ASTClass.instrum("Expression Statement","138");
			//ASTClass.instrum("Expression Statement","167");
			//ASTClass.instrum("Expression Statement","196");
			} else {
                if(filter.getEndOn() != null) {
                    criteria.add(Restrictions.le("transactionOn", filter.getEndOn()));
					//ASTClass.instrum("Expression Statement","112");
					//ASTClass.instrum("Expression Statement","142");
					//ASTClass.instrum("Expression Statement","172");
					//ASTClass.instrum("Expression Statement","202");
                }
				//ASTClass.instrum("If Statement","111");
				//ASTClass.instrum("If Statement","141");
				//ASTClass.instrum("If Statement","171");
				//ASTClass.instrum("If Statement","201");
        }
			//ASTClass.instrum("If Statement","108");
			//ASTClass.instrum("If Statement","137");
			//ASTClass.instrum("If Statement","166");
			//ASTClass.instrum("If Statement","195");
		}
		//ASTClass.instrum("If Statement","105");
		//ASTClass.instrum("If Statement","133");
		//ASTClass.instrum("If Statement","161");
		//ASTClass.instrum("If Statement","189");

        if(Boolean.TRUE.equals(filter.getAuto())) {
            criteria.add(Restrictions.eq("auto", Boolean.TRUE));
			//ASTClass.instrum("Expression Statement","118");
			//ASTClass.instrum("Expression Statement","152");
			//ASTClass.instrum("Expression Statement","186");
			//ASTClass.instrum("Expression Statement","220");
        }
		//ASTClass.instrum("If Statement","117");
		//ASTClass.instrum("If Statement","151");
		//ASTClass.instrum("If Statement","185");
		//ASTClass.instrum("If Statement","219");

        return list(criteria);
    }


    private Criteria defaultCriteria() {
        Criteria criteria = currentSession().createCriteria(Transaction.class, "t");
		//ASTClass.instrum("Variable Declaration Statement","234");
		//ASTClass.instrum("Variable Declaration Statement","198");
		//ASTClass.instrum("Variable Declaration Statement","162");
		//ASTClass.instrum("Variable Declaration Statement","126");
        criteria.addOrder(Order.desc("transactionOn"));
		//ASTClass.instrum("Expression Statement","238");
		//ASTClass.instrum("Expression Statement","201");
		//ASTClass.instrum("Expression Statement","164");
		//ASTClass.instrum("Expression Statement","127");
        criteria.addOrder(Order.desc("id"));
		//ASTClass.instrum("Expression Statement","242");
		//ASTClass.instrum("Expression Statement","204");
		//ASTClass.instrum("Expression Statement","166");
		//ASTClass.instrum("Expression Statement","128");
        return criteria;
    }

    public void delete(Transaction transaction) {
        currentSession().delete(transaction);
		//ASTClass.instrum("Expression Statement","transaction,",currentSession().delete(transaction)","133");
		//ASTClass.instrum("Expression Statement","transaction,",currentSession().delete(transaction)","172");
		//ASTClass.instrum("Expression Statement","transaction,",currentSession().delete(transaction)","211");
		//ASTClass.instrum("Expression Statement","transaction,",currentSession().delete(transaction)","250");
    }
}
