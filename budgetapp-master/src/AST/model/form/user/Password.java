package io.budgetapp.model.form.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Password implements Serializable {

    private static final long serialVersionUID = 8359006914394898221L;

    private String original;
    private String password;
    private String confirm;

    @NotBlank(message = "{validation.password.original.required}")
    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
    }

    @NotBlank(message = "{validation.password.required}")
    @Size.List({@Size(min = 6, message = "{validation.password.length}")})
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","40");
    }

    @NotBlank(message = "{validation.password.confirm.required}")
    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
		//ASTClass.instrum("Expression Statement","confirm,",this.confirm=confirm","46");
		//ASTClass.instrum("Expression Statement","confirm,",this.confirm=confirm","48");
		//ASTClass.instrum("Expression Statement","confirm,",this.confirm=confirm","50");
		//ASTClass.instrum("Expression Statement","confirm,",this.confirm=confirm","52");
    }
}
