package io.budgetapp.model;

import java.io.Serializable;

/**
 *
 */
public class UsageSummary implements Serializable {
    private static final long serialVersionUID = 3151593231290523047L;

    private double income;
    private double projected;
    private double actual;

    public UsageSummary(double income, double projected, double actual) {
        this.income = income;
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
        this.projected = projected;
		//ASTClass.instrum("Expression Statement","20");
		//ASTClass.instrum("Expression Statement","19");
		//ASTClass.instrum("Expression Statement","18");
		//ASTClass.instrum("Expression Statement","17");
        this.actual = actual;
		//ASTClass.instrum("Expression Statement","18");
		//ASTClass.instrum("Expression Statement","20");
		//ASTClass.instrum("Expression Statement","22");
		//ASTClass.instrum("Expression Statement","24");
    }

    public double getIncome() {
        return income;
    }

    public double getProjected() {
        return projected;
    }

    public double getActual() {
        return actual;
    }

    public double getRemaining() {
        return projected - actual;
    }
}
