package io.budgetapp.dao;

import io.budgetapp.application.NotFoundException;
import io.budgetapp.model.User;
import io.budgetapp.model.form.SignUpForm;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public class UserDAO extends AbstractDAO<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findById(Long userId) {
        User user = get(userId);
		ASTClass.instrum("Variable Declaration Statement","28");
        if(user == null) {

            throw new NotFoundException();
        }
		ASTClass.instrum("If Statement","29");
        //ASTClass.instrum( "hi","hello" );
        return user;
    }

    public User add(SignUpForm signUp) {
        LOGGER.debug("Add new user {}", signUp);
		ASTClass.instrum("Expression Statement","38");
        User user = new User();
		ASTClass.instrum("Variable Declaration Statement","39");
        user.setUsername(signUp.getUsername());
		ASTClass.instrum("Expression Statement","40");
        user.setPassword(signUp.getPassword());
		ASTClass.instrum("Expression Statement","41");
        user = persist(user);
		ASTClass.instrum("Expression Statement","42");
        return user;
    }

    public void update(User user) {
        LOGGER.debug("Update user {}", user);
		ASTClass.instrum("Expression Statement","47");
        persist(user);
		ASTClass.instrum("Expression Statement","48");
    }

    public Optional<User> findByUsername(String username) {
        Criteria criteria = criteria();
		ASTClass.instrum("Variable Declaration Statement","52");
        criteria.add(Restrictions.eq("username", username).ignoreCase());
		ASTClass.instrum("Expression Statement","53");
        List<User> users = list(criteria);
		ASTClass.instrum("Variable Declaration Statement","54");
        if(users.size() == 1) {
            return Optional.of(users.get(0));
        } else {
            return Optional.empty();
        }
		//ASTClass.instrum("If Statement","55");
    }
}
