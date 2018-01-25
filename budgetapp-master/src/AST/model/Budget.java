package io.budgetapp.model;

import io.budgetapp.model.form.budget.AddBudgetForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "budgets")
public class Budget implements Serializable {

    private static final long serialVersionUID = 2625666273036891436L;

    private Long id;
    private String name;
    private double projected;
    private double actual;
    private Date period;
    private Date createdAt;
    private User user;
    private Category category;
    private BudgetType budgetType;

    public Budget() {
    }

    public Budget(long budgetId) {
        this.id = budgetId;
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
    }

    public Budget(AddBudgetForm budgetForm) {
        setName(budgetForm.getName());
		//ASTClass.instrum("Expression Statement","40");
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","37");
        setProjected(budgetForm.getProjected());
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","42");
		//ASTClass.instrum("Expression Statement","40");
		//ASTClass.instrum("Expression Statement","38");
        setCategory(new Category(budgetForm.getCategoryId()));
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","42");
		//ASTClass.instrum("Expression Statement","45");
		//ASTClass.instrum("Expression Statement","48");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","53");
		//ASTClass.instrum("Expression Statement","57");
		//ASTClass.instrum("Expression Statement","61");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","57");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","67");
		//ASTClass.instrum("Expression Statement","72");
    }

    public double getProjected() {
        return projected;
    }

    public void setProjected(double projected) {
        this.projected = projected;
		//ASTClass.instrum("Expression Statement","65");
		//ASTClass.instrum("Expression Statement","71");
		//ASTClass.instrum("Expression Statement","77");
		//ASTClass.instrum("Expression Statement","83");
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
		//ASTClass.instrum("Expression Statement","73");
		//ASTClass.instrum("Expression Statement","80");
		//ASTClass.instrum("Expression Statement","87");
		//ASTClass.instrum("Expression Statement","94");
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "period_on", nullable = false, updatable = false)
    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
		//ASTClass.instrum("Expression Statement","84");
		//ASTClass.instrum("Expression Statement","92");
		//ASTClass.instrum("Expression Statement","100");
		//ASTClass.instrum("Expression Statement","108");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","94");
		//ASTClass.instrum("Expression Statement","103");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","121");
    }

    @JsonIgnore
    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","125");
		//ASTClass.instrum("Expression Statement","135");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","126");
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","148");
    }

    @JoinColumn(name = "type_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
		//ASTClass.instrum("Expression Statement","125");
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","149");
		//ASTClass.instrum("Expression Statement","161");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Budget{");
		//ASTClass.instrum("Variable Declaration Statement","169");
		//ASTClass.instrum("Variable Declaration Statement","156");
		//ASTClass.instrum("Variable Declaration Statement","143");
		//ASTClass.instrum("Variable Declaration Statement","130");
        sb.append("id=").append(id);
		//ASTClass.instrum("Expression Statement","173");
		//ASTClass.instrum("Expression Statement","159");
		//ASTClass.instrum("Expression Statement","145");
		//ASTClass.instrum("Expression Statement","131");
        sb.append(", actual=").append(actual);
		//ASTClass.instrum("Expression Statement","177");
		//ASTClass.instrum("Expression Statement","162");
		//ASTClass.instrum("Expression Statement","147");
		//ASTClass.instrum("Expression Statement","132");
        sb.append(", name='").append(name).append('\'');
		//ASTClass.instrum("Expression Statement","181");
		//ASTClass.instrum("Expression Statement","165");
		//ASTClass.instrum("Expression Statement","149");
		//ASTClass.instrum("Expression Statement","133");
        sb.append(", createdAt=").append(createdAt);
		//ASTClass.instrum("Expression Statement","185");
		//ASTClass.instrum("Expression Statement","168");
		//ASTClass.instrum("Expression Statement","151");
		//ASTClass.instrum("Expression Statement","134");
        sb.append(", user=").append(user);
		//ASTClass.instrum("Expression Statement","189");
		//ASTClass.instrum("Expression Statement","171");
		//ASTClass.instrum("Expression Statement","153");
		//ASTClass.instrum("Expression Statement","135");
        sb.append(", category=").append(category);
		//ASTClass.instrum("Expression Statement","193");
		//ASTClass.instrum("Expression Statement","174");
		//ASTClass.instrum("Expression Statement","155");
		//ASTClass.instrum("Expression Statement","136");
        sb.append('}');
		//ASTClass.instrum("Expression Statement","197");
		//ASTClass.instrum("Expression Statement","177");
		//ASTClass.instrum("Expression Statement","157");
		//ASTClass.instrum("Expression Statement","137");
        return sb.toString();
    }
}
