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
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
        setName(name);
		//ASTClass.instrum("Expression Statement","22");
		//ASTClass.instrum("Expression Statement","23");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","25");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","32");
		//ASTClass.instrum("Expression Statement","34");
		//ASTClass.instrum("Expression Statement","36");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","47");
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","50");
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","58");
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","59");
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","69");
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","80");
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
		//ASTClass.instrum("Expression Statement","budgets,",this.budgets=budgets","70");
		//ASTClass.instrum("Expression Statement","budgets,",this.budgets=budgets","77");
		//ASTClass.instrum("Expression Statement","budgets,",this.budgets=budgets","84");
		//ASTClass.instrum("Expression Statement","budgets,",this.budgets=budgets","91");
    }
}
