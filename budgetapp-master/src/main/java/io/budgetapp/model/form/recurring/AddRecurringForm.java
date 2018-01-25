package io.budgetapp.model.form.recurring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.budgetapp.model.RecurringType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddRecurringForm implements Serializable {

    private static final long serialVersionUID = -3317443535487916735L;

    private Long budgetId;
    private Double amount;
    private Date recurringAt;
    private RecurringType recurringType;
    private String remark;

    @NotNull(message = "{validation.budget.required}")
    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
		ASTClass.instrum("Expression Statement","30");
    }

    @NotNull(message = "{validation.amount.required}")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
		ASTClass.instrum("Expression Statement","39");
    }

    @NotNull(message = "{validation.recurringAt.required}")
    public Date getRecurringAt() {
        return recurringAt;
    }

    public void setRecurringAt(Date recurringAt) {
        this.recurringAt = recurringAt;
		ASTClass.instrum("Expression Statement","48");
    }

    @NotNull(message = "{validation.recurringType.required}")
    public RecurringType getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
		ASTClass.instrum("Expression Statement","57");
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
		ASTClass.instrum("Expression Statement","remark","this.remark=remark","65");
    }
}
