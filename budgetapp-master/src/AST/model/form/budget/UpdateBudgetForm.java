package io.budgetapp.model.form.budget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBudgetForm extends BudgetForm implements Serializable {

    private static final long serialVersionUID = 7677505567308081026L;

    private Long id;
    private double actual;

    @NotNull(message = "{validation.id.required}")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","25");
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
		//ASTClass.instrum("Expression Statement","actual,",this.actual=actual","33");
		//ASTClass.instrum("Expression Statement","actual,",this.actual=actual","34");
		//ASTClass.instrum("Expression Statement","actual,",this.actual=actual","35");
		//ASTClass.instrum("Expression Statement","actual,",this.actual=actual","36");
    }
}
