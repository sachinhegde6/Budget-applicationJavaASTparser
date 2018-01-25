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
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
    }

    public List<Category> findCategories(User user) {
        Criteria criteria = userCriteria(user);
		//ASTClass.instrum("Variable Declaration Statement","37");
		//ASTClass.instrum("Variable Declaration Statement","36");
		//ASTClass.instrum("Variable Declaration Statement","35");
		//ASTClass.instrum("Variable Declaration Statement","34");
        criteria.addOrder(Order.desc("type"));
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","35");
        return list(criteria);
    }

    public Collection<Category> addDefaultCategories(User user) {
        Collection<Category> categories = new ArrayList<>(configuration.getCategories().size());
		//ASTClass.instrum("Variable Declaration Statement","49");
		//ASTClass.instrum("Variable Declaration Statement","46");
		//ASTClass.instrum("Variable Declaration Statement","43");
		//ASTClass.instrum("Variable Declaration Statement","40");
        configuration.getCategories()
                .forEach(c -> categories.add(addCategory(user, category(c))));
		//ASTClass.instrum("Expression Statement","53");
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","45");
		//ASTClass.instrum("Expression Statement","41");
        return categories;
    }

    public Category addCategory(User user, Category category) {
        LOGGER.debug("Add new category {}", category);
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","57");
		//ASTClass.instrum("Expression Statement","52");
		//ASTClass.instrum("Expression Statement","47");
        category.setUser(user);
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","60");
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","48");
        return persist(category);
    }

    public Category findById(long categoryId) {
        Category category = get(categoryId);
		//ASTClass.instrum("Variable Declaration Statement","74");
		//ASTClass.instrum("Variable Declaration Statement","67");
		//ASTClass.instrum("Variable Declaration Statement","60");
		//ASTClass.instrum("Variable Declaration Statement","53");
        if(category == null) {
            throw new NotFoundException();
        }
		//ASTClass.instrum("If Statement","54");
		//ASTClass.instrum("If Statement","62");
		//ASTClass.instrum("If Statement","70");
		//ASTClass.instrum("If Statement","78");

        return category;
    }

    private Category category(Category ori) {
        Category category = new Category();
		//ASTClass.instrum("Variable Declaration Statement","89");
		//ASTClass.instrum("Variable Declaration Statement","80");
		//ASTClass.instrum("Variable Declaration Statement","71");
		//ASTClass.instrum("Variable Declaration Statement","62");
        category.setName(ori.getName());
		//ASTClass.instrum("Expression Statement","93");
		//ASTClass.instrum("Expression Statement","83");
		//ASTClass.instrum("Expression Statement","73");
		//ASTClass.instrum("Expression Statement","63");
        category.setType(ori.getType());
		//ASTClass.instrum("Expression Statement","97");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","75");
		//ASTClass.instrum("Expression Statement","64");
        return category;
    }

    public Category find(User user, long categoryId) {
        Criteria criteria = userCriteria(user);
		//ASTClass.instrum("Variable Declaration Statement","105");
		//ASTClass.instrum("Variable Declaration Statement","93");
		//ASTClass.instrum("Variable Declaration Statement","81");
		//ASTClass.instrum("Variable Declaration Statement","69");
        criteria.add(Restrictions.eq("id", categoryId));
		//ASTClass.instrum("Expression Statement","70");
		//ASTClass.instrum("Expression Statement","83");
		//ASTClass.instrum("Expression Statement","96");
		//ASTClass.instrum("Expression Statement","109");

        return singleResult(criteria);
    }

    private Criteria defaultCriteria() {
        return criteria();
    }

    private Criteria userCriteria(User user) {
        Criteria criteria = defaultCriteria();
		//ASTClass.instrum("Variable Declaration Statement","122");
		//ASTClass.instrum("Variable Declaration Statement","108");
		//ASTClass.instrum("Variable Declaration Statement","94");
		//ASTClass.instrum("Variable Declaration Statement","80");
        criteria.add(Restrictions.eq("user", user));
		//ASTClass.instrum("Expression Statement","126");
		//ASTClass.instrum("Expression Statement","111");
		//ASTClass.instrum("Expression Statement","96");
		//ASTClass.instrum("Expression Statement","81");
        return criteria;
    }

    public void delete(Category category) {
        currentSession().delete(category);
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","102");
		//ASTClass.instrum("Expression Statement","118");
		//ASTClass.instrum("Expression Statement","134");
    }

    public List<String> findSuggestions(User user, String q) {
        q = q == null? "": q.toLowerCase();
		//ASTClass.instrum("Expression Statement","90");
		//ASTClass.instrum("Expression Statement","107");
		//ASTClass.instrum("Expression Statement","124");
		//ASTClass.instrum("Expression Statement","141");

        Query<String> query = currentSession().createQuery("SELECT c.name FROM Category c WHERE c.user != :user AND LOWER(c.name) LIKE :q", String.class);
		//ASTClass.instrum("Variable Declaration Statement","146");
		//ASTClass.instrum("Variable Declaration Statement","128");
		//ASTClass.instrum("Variable Declaration Statement","110");
		//ASTClass.instrum("Variable Declaration Statement","92");
        query
                .setParameter("user", user)
                .setParameter("q", "%" + q + "%");
		//ASTClass.instrum("Expression Statement","93");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","131");
		//ASTClass.instrum("Expression Statement","150");

        return query.list();
    }
}
