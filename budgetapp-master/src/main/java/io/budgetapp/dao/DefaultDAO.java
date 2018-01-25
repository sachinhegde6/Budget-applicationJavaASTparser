package io.budgetapp.dao;

import io.budgetapp.application.NotFoundException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

/**
 *
 */
public class DefaultDAO<T> extends AbstractDAO<T> {


    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public DefaultDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }



    protected final <T> T singleResult(Criteria criteria) {
        T t = (T)uniqueResult(criteria);
		ASTClass.instrum("Variable Declaration Statement","26");
        if(t == null) {
            throw new NotFoundException();
        }
		ASTClass.instrum("If Statement","27");

        return t;
    }

}
