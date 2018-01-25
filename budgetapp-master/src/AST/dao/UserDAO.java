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
		//ASTClass.instrum("Variable Declaration Statement","28");
		//ASTClass.instrum("Variable Declaration Statement","28");
		//ASTClass.instrum("Variable Declaration Statement","28");
		//ASTClass.instrum("Variable Declaration Statement","28");
        if(user == null) {

            throw new NotFoundException();
        }
		//ASTClass.instrum("If Statement","32");
		//ASTClass.instrum("If Statement","31");
		//ASTClass.instrum("If Statement","30");
		//ASTClass.instrum("If Statement","29");
        ASTClass.instrum( "hi","hello" );
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","35");
		//ASTClass.instrum("Expression Statement","33");
        return user;
    }

    public User add(SignUpForm signUp) {
        LOGGER.debug("Add new user {}", signUp);
		//ASTClass.instrum("Expression Statement","47");
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","38");
        User user = new User();
		//ASTClass.instrum("Variable Declaration Statement","51");
		//ASTClass.instrum("Variable Declaration Statement","47");
		//ASTClass.instrum("Variable Declaration Statement","43");
		//ASTClass.instrum("Variable Declaration Statement","39");
        user.setUsername(signUp.getUsername());
		//ASTClass.instrum("Expression Statement","55");
		//ASTClass.instrum("Expression Statement","50");
		//ASTClass.instrum("Expression Statement","45");
		//ASTClass.instrum("Expression Statement","40");
        user.setPassword(signUp.getPassword());
		//ASTClass.instrum("Expression Statement","59");
		//ASTClass.instrum("Expression Statement","53");
		//ASTClass.instrum("Expression Statement","47");
		//ASTClass.instrum("Expression Statement","41");
        user = persist(user);
		//ASTClass.instrum("Expression Statement","63");
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","42");
        return user;
    }

    public void update(User user) {
        LOGGER.debug("Update user {}", user);
		//ASTClass.instrum("Expression Statement","71");
		//ASTClass.instrum("Expression Statement","63");
		//ASTClass.instrum("Expression Statement","55");
		//ASTClass.instrum("Expression Statement","47");
        persist(user);
		//ASTClass.instrum("Expression Statement","48");
		//ASTClass.instrum("Expression Statement","57");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","75");
    }

    public Optional<User> findByUsername(String username) {
        Criteria criteria = criteria();
		//ASTClass.instrum("Variable Declaration Statement","82");
		//ASTClass.instrum("Variable Declaration Statement","72");
		//ASTClass.instrum("Variable Declaration Statement","62");
		//ASTClass.instrum("Variable Declaration Statement","52");
        criteria.add(Restrictions.eq("username", username).ignoreCase());
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","75");
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","53");
        List<User> users = list(criteria);
		//ASTClass.instrum("Variable Declaration Statement","90");
		//ASTClass.instrum("Variable Declaration Statement","78");
		//ASTClass.instrum("Variable Declaration Statement","66");
		//ASTClass.instrum("Variable Declaration Statement","54");
        if(users.size() == 1) {
            return Optional.of(users.get(0));
        } else {
            return Optional.empty();
        }
		//ASTClass.instrum("If Statement","55");
		//ASTClass.instrum("If Statement","68");
		//ASTClass.instrum("If Statement","81");
		//ASTClass.instrum("If Statement","94");
    }
}
