package io.budgetapp.dao;

import io.budgetapp.model.AuthToken;
import io.budgetapp.model.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */
public class AuthTokenDAO extends AbstractDAO<AuthToken> {

    public AuthTokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public AuthToken add(User user) {
        AuthToken authToken = new AuthToken();
		ASTClass.instrum("Variable Declaration Statement","24");
        authToken.setUser(user);
		ASTClass.instrum("Expression Statement","25");
        authToken.setToken(newToken());
		ASTClass.instrum("Expression Statement","26");
        return persist(authToken);
    }

    public Optional<AuthToken> find(String token) {
        Criteria criteria = criteria();
		ASTClass.instrum("Variable Declaration Statement","31");
        criteria.add(Restrictions.eq("token", token));
		ASTClass.instrum("Expression Statement","32");
        return Optional.ofNullable(uniqueResult(criteria));
    }

    public List<AuthToken> findByUser(User user) {
        Criteria criteria = criteria();
		ASTClass.instrum("Variable Declaration Statement","37");
        criteria.add(Restrictions.eq("user", user));
		ASTClass.instrum("Expression Statement","38");
        return list(criteria);
    }

    private String newToken() {
        return UUID.randomUUID().toString();
    }
}
