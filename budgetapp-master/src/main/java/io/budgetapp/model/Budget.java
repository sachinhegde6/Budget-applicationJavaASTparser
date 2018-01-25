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
		ASTClass.instrum("Expression Statement","33");
    }

    public Budget(AddBudgetForm budgetForm) {
        setName(budgetForm.getName());
		ASTClass.instrum("Expression Statement","37");
        setProjected(budgetForm.getProjected());
		ASTClass.instrum("Expression Statement","38");
        setCategory(new Category(budgetForm.getCategoryId()));
		ASTClass.instrum("Expression Statement","39");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		ASTClass.instrum("Expression Statement","49");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		ASTClass.instrum("Expression Statement","57");
    }

    public double getProjected() {
        return projected;
    }

    public void setProjected(double projected) {
        this.projected = projected;
		ASTClass.instrum("Expression Statement","65");
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
		ASTClass.instrum("Expression Statement","73");
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "period_on", nullable = false, updatable = false)
    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
		ASTClass.instrum("Expression Statement","84");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		ASTClass.instrum("Expression Statement","94");
    }

    @JsonIgnore
    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
		ASTClass.instrum("Expression Statement","105");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
		ASTClass.instrum("Expression Statement","115");
    }

    @JoinColumn(name = "type_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
		ASTClass.instrum("Expression Statement","125");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Budget{");
		ASTClass.instrum("Variable Declaration Statement","130");
        sb.append("id=").append(id);
		ASTClass.instrum("Expression Statement","131");
        sb.append(", actual=").append(actual);
		ASTClass.instrum("Expression Statement","132");
        sb.append(", name='").append(name).append('\'');
		ASTClass.instrum("Expression Statement","133");
        sb.append(", createdAt=").append(createdAt);
		ASTClass.instrum("Expression Statement","134");
        sb.append(", user=").append(user);
		ASTClass.instrum("Expression Statement","135");
        sb.append(", category=").append(category);
		ASTClass.instrum("Expression Statement","136");
        sb.append('}');
		ASTClass.instrum("Expression Statement","137");
        return sb.toString();
    }
}
