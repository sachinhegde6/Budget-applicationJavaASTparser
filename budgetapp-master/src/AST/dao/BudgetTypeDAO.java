package io.budgetapp.dao;

import io.budgetapp.model.BudgetType;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 *
 */
public class BudgetTypeDAO extends AbstractDAO<BudgetType> {

    public BudgetTypeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public BudgetType addBudgetType() {
        BudgetType budgetType = new BudgetType();
		//ASTClass.instrum("Variable Declaration Statement","17");
		//ASTClass.instrum("Variable Declaration Statement","17");
		//ASTClass.instrum("Variable Declaration Statement","17");
		//ASTClass.instrum("Variable Declaration Statement","17");
        return persist(budgetType);
    }
}
