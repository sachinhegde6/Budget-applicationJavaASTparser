package io.budgetapp.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.budgetapp.model.Budget;
import io.budgetapp.model.RecurringType;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionForm implements Serializable {

    private static final long serialVersionUID = 1432079737348530213L;

    private double amount;
    private String remark;
    private Date transactionOn;
    private Boolean recurring;
    private RecurringType recurringType;
    private Budget budget;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
		//ASTClass.instrum("Expression Statement","30");
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","40");
		//ASTClass.instrum("Expression Statement","41");
    }

    public Date getTransactionOn() {
        if(transactionOn == null) {
            return new Date();
        } else {
            return transactionOn;
        }
		//ASTClass.instrum("If Statement","42");
		//ASTClass.instrum("If Statement","44");
		//ASTClass.instrum("If Statement","46");
		//ASTClass.instrum("If Statement","48");
    }

    public void setTransactionOn(Date transactionOn) {
        this.transactionOn = transactionOn;
		//ASTClass.instrum("Expression Statement","50");
		//ASTClass.instrum("Expression Statement","53");
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","59");
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
		//ASTClass.instrum("Expression Statement","58");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","70");
    }

    public RecurringType getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","71");
		//ASTClass.instrum("Expression Statement","76");
		//ASTClass.instrum("Expression Statement","81");
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","80");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","92");
    }

    @Override
    public String toString() {
        return "TransactionForm{" +
                "amount=" + amount +
                ", remark='" + remark + '\'' +
                ", transactionOn=" + transactionOn +
                ", recurring=" + recurring +
                ", recurringType=" + recurringType +
                ", budget=" + budget +
                '}';
    }
}
