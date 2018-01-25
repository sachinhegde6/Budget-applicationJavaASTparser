package io.budgetapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AccountSummary implements Serializable {

    private static final long serialVersionUID = 5319703962527161534L;

    private List<Budget> income;
    private List<Group> groups = new ArrayList<>();

    public List<Budget> getIncome() {
        return income;
    }

    public void setIncome(List<Budget> income) {
        this.income = income;
		//ASTClass.instrum("Expression Statement","22");
		//ASTClass.instrum("Expression Statement","22");
		//ASTClass.instrum("Expression Statement","22");
		//ASTClass.instrum("Expression Statement","22");
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
		//ASTClass.instrum("Expression Statement","groups,",this.groups=groups","30");
		//ASTClass.instrum("Expression Statement","groups,",this.groups=groups","31");
		//ASTClass.instrum("Expression Statement","groups,",this.groups=groups","32");
		//ASTClass.instrum("Expression Statement","groups,",this.groups=groups","33");
    }
}
