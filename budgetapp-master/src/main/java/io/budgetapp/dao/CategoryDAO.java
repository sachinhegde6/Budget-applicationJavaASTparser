package io.budgetapp.dao;

import io.budgetapp.application.NotFoundException;
import io.budgetapp.configuration.AppConfiguration;
import io.budgetapp.model.Category;
import io.budgetapp.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class CategoryDAO extends DefaultDAO<Category> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDAO.class);

    private final AppConfiguration configuration;

    public CategoryDAO(SessionFactory sessionFactory, AppConfiguration configuration) {
        super(sessionFactory);
        this.configuration = configuration;
		ASTClass.instrum("Expression Statement","30");
    }

    public List<Category> findCategories(User user) {
        Criteria criteria = userCriteria(user);
		ASTClass.instrum("Variable Declaration Statement","34");
        criteria.addOrder(Order.desc("type"));
		ASTClass.instrum("Expression Statement","35");
        return list(criteria);
    }

    public Collection<Category> addDefaultCategories(User user) {
        Collection<Category> categories = new ArrayList<>(configuration.getCategories().size());
		ASTClass.instrum("Variable Declaration Statement","40");
        configuration.getCategories()
                .forEach(c -> categories.add(addCategory(user, category(c))));
		ASTClass.instrum("Expression Statement","41");
        return categories;
    }

    public Category addCategory(User user, Category category) {
        LOGGER.debug("Add new category {}", category);
		ASTClass.instrum("Expression Statement","47");
        category.setUser(user);
		ASTClass.instrum("Expression Statement","48");
        return persist(category);
    }

    public Category findById(long categoryId) {
        Category category = get(categoryId);
		ASTClass.instrum("Variable Declaration Statement","53");
        if(category == null) {
            throw new NotFoundException();
        }
		ASTClass.instrum("If Statement","54");

        return category;
    }

    private Category category(Category ori) {
        Category category = new Category();
		ASTClass.instrum("Variable Declaration Statement","62");
        category.setName(ori.getName());
		ASTClass.instrum("Expression Statement","63");
        category.setType(ori.getType());
		ASTClass.instrum("Expression Statement","64");
        return category;
    }

    public Category find(User user, long categoryId) {
        Criteria criteria = userCriteria(user);
		ASTClass.instrum("Variable Declaration Statement","69");
        criteria.add(Restrictions.eq("id", categoryId));
		ASTClass.instrum("Expression Statement","70");

        return singleResult(criteria);
    }

    private Criteria defaultCriteria() {
        return criteria();
    }

    private Criteria userCriteria(User user) {
        Criteria criteria = defaultCriteria();
		ASTClass.instrum("Variable Declaration Statement","80");
        criteria.add(Restrictions.eq("user", user));
		ASTClass.instrum("Expression Statement","81");
        return criteria;
    }

    public void delete(Category category) {
        currentSession().delete(category);
		ASTClass.instrum("Expression Statement","86");
    }

    public List<String> findSuggestions(User user, String q) {
        q = q == null? "": q.toLowerCase();
		ASTClass.instrum("Expression Statement","90");

        Query<String> query = currentSession().createQuery("SELECT c.name FROM Category c WHERE c.user != :user AND LOWER(c.name) LIKE :q", String.class);
		ASTClass.instrum("Variable Declaration Statement","92");
        query
                .setParameter("user", user)
                .setParameter("q", "%" + q + "%");
		ASTClass.instrum("Expression Statement","93");

        return query.list();
    }
}
