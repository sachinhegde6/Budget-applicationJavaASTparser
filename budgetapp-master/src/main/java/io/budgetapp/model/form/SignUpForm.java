package io.budgetapp.model.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 */
public class SignUpForm implements Serializable {

    private static final long serialVersionUID = -3387043933342205884L;

    private String username;
    private String password;

    @NotBlank(message = "{validation.username.required}")
    @Email(message = "{validation.username.invalid}")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
		ASTClass.instrum("Expression Statement","26");
    }

    @NotBlank(message = "{validation.password.required}")
    @Size.List({@Size(min = 6, message = "{validation.password.length}")})
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
		ASTClass.instrum("Expression Statement","36");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignUpForm{");
		ASTClass.instrum("Variable Declaration Statement","41");
        sb.append("username='").append(username).append('\'');
		ASTClass.instrum("Expression Statement","42");
        sb.append(", password='").append(password).append('\'');
		ASTClass.instrum("Expression Statement","43");
        sb.append('}');
		ASTClass.instrum("Expression Statement","44");
        return sb.toString();
    }
}
