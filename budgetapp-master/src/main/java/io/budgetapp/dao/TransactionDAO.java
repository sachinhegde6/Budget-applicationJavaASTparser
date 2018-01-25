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
		ASTClass.instrum("Expression Statement","33");
        return persist(transaction);
    }

    public List<Transaction> find(User user, Integer limit) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user ORDER BY t.transactionOn DESC, t.id ASC");
		ASTClass.instrum("Variable Declaration Statement","38");
        query.setParameter("user", user);
		ASTClass.instrum("Expression Statement","39");
        query.setMaxResults(limit);
		ASTClass.instrum("Expression Statement","40");
        return list(query);
    }

    public Transaction findById(long id) {
        Transaction transaction = get(id);
		ASTClass.instrum("Variable Declaration Statement","45");
        if(transaction == null) {
            throw new NotFoundException();
        }
		ASTClass.instrum("If Statement","46");

        return transaction;
    }

    public Optional<Transaction> findById(User user, long id) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.id = :id AND t.budget.user = :user");
		ASTClass.instrum("Variable Declaration Statement","54");
        query.setParameter("user", user);
		ASTClass.instrum("Expression Statement","55");
        query.setParameter("id", id);
		ASTClass.instrum("Expression Statement","56");
        Transaction result = query.uniqueResult();
		ASTClass.instrum("Variable Declaration Statement","57");
        return Optional.ofNullable(result);
    }

    public List<Transaction> findByBudget(User user, long budgetId) {
        Criteria criteria = defaultCriteria();
		ASTClass.instrum("Variable Declaration Statement","62");
        criteria.createAlias("t.budget", "budget");
		ASTClass.instrum("Expression Statement","63");

        criteria.add(Restrictions.eq("budget.id", budgetId));
		ASTClass.instrum("Expression Statement","65");
        criteria.add(Restrictions.eq("budget.user", user));
		ASTClass.instrum("Expression Statement","66");
        return list(criteria);
    }

    public List<Transaction> findByRecurring(User user, long recurringId) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user AND t.recurring.id = :recurringId ORDER BY t.transactionOn DESC, t.id ASC");
		ASTClass.instrum("Variable Declaration Statement","71");
        query.setParameter("user", user);
		ASTClass.instrum("Expression Statement","72");
        query.setParameter("recurringId", recurringId);
		ASTClass.instrum("Expression Statement","73");
        return list(query);
    }

    public List<Transaction> findByRange(User user, Date start, Date end) {
        Query<Transaction> query = query("FROM Transaction t WHERE t.budget.user = :user AND t.transactionOn BETWEEN :start AND :end ORDER BY t.transactionOn DESC, t.id ASC");
		ASTClass.instrum("Variable Declaration Statement","78");
        query
                .setParameter("user", user)
                .setParameter("start", start)
                .setParameter("end", end);
		ASTClass.instrum("Expression Statement","79");

        return list(query);
    }

    public List<Transaction> findTransactions(User user, SearchFilter filter) {
        Criteria criteria = defaultCriteria();
		ASTClass.instrum("Variable Declaration Statement","88");
        criteria.createAlias("t.budget", "budget");
		ASTClass.instrum("Expression Statement","89");

        criteria.add(Restrictions.eq("budget.user", user));
		ASTClass.instrum("Expression Statement","91");

        if(filter.isAmountRange()) {
            criteria.add(Restrictions.between("amount", filter.getMinAmount(), filter.getMaxAmount()));
			ASTClass.instrum("Expression Statement","94");
        } else {
			if(filter.getMinAmount() != null) {
            criteria.add(Restrictions.ge("amount", filter.getMinAmount()));
			ASTClass.instrum("Expression Statement","97");
			} else {
                if (filter.getMaxAmount() != null) {
                    criteria.add(Restrictions.le("amount", filter.getMaxAmount()));
					ASTClass.instrum("Expression Statement","100");
                }
				ASTClass.instrum("If Statement","99");
        }
			ASTClass.instrum("If Statement","96");
		}
		ASTClass.instrum("If Statement","93");

        if(filter.isDateRange()) {
            criteria.add(Restrictions.between("transactionOn", filter.getStartOn(), filter.getEndOn()));
			ASTClass.instrum("Expression Statement","106");
        } else {
			if(filter.getStartOn() != null) {
            criteria.add(Restrictions.ge("transactionOn", filter.getStartOn()));
			ASTClass.instrum("Expression Statement","109");
			} else {
                if(filter.getEndOn() != null) {
                    criteria.add(Restrictions.le("transactionOn", filter.getEndOn()));
					ASTClass.instrum("Expression Statement","112");
                }
				ASTClass.instrum("If Statement","111");
        }
			ASTClass.instrum("If Statement","108");
		}
		ASTClass.instrum("If Statement","105");

        if(Boolean.TRUE.equals(filter.getAuto())) {
            criteria.add(Restrictions.eq("auto", Boolean.TRUE));
			ASTClass.instrum("Expression Statement","118");
        }
		ASTClass.instrum("If Statement","117");

        return list(criteria);
    }


    private Criteria defaultCriteria() {
        Criteria criteria = currentSession().createCriteria(Transaction.class, "t");
		ASTClass.instrum("Variable Declaration Statement","126");
        criteria.addOrder(Order.desc("transactionOn"));
		ASTClass.instrum("Expression Statement","127");
        criteria.addOrder(Order.desc("id"));
		ASTClass.instrum("Expression Statement","128");
        return criteria;
    }

    public void delete(Transaction transaction) {
        currentSession().delete(transaction);
		ASTClass.instrum("Expression Statement","transaction","currentSession().delete(transaction)","133");
    }
}
