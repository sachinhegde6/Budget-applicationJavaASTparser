package io.budgetapp.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Group implements Serializable {

    private static final long serialVersionUID = -3295824982209236728L;

    private Long id;
    private String name;
    private CategoryType type;
    private double budget;
    private double spent;
    private List<Budget> budgets;

    public Group(Long id, String name) {
        setId(id);
		ASTClass.instrum("Expression Statement","21");
        setName(name);
		ASTClass.instrum("Expression Statement","22");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		ASTClass.instrum("Expression Statement","30");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		ASTClass.instrum("Expression Statement","38");
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
		ASTClass.instrum("Expression Statement","46");
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
		ASTClass.instrum("Expression Statement","54");
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
		ASTClass.instrum("Expression Statement","62");
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
		ASTClass.instrum("Expression Statement","budgets","this.budgets=budgets","70");
    }
}
