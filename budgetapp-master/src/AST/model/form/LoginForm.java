package io.budgetapp.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginForm implements Serializable {

    private static final long serialVersionUID = -1155392015861302100L;

    private String username;
    private String password;

    // Jackson required
    public LoginForm() {
    }

    public LoginForm(String username, String password) {
        this.username = username;
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
        this.password = password;
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","26");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","28");
    }

    @NotBlank(message = "{validation.username.required}")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
		//ASTClass.instrum("Expression Statement","34");
		//ASTClass.instrum("Expression Statement","36");
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","40");
    }

    @NotBlank(message = "{validation.password.required}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
		//ASTClass.instrum("Expression Statement","43");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","49");
		//ASTClass.instrum("Expression Statement","52");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginForm{");
		//ASTClass.instrum("Variable Declaration Statement","60");
		//ASTClass.instrum("Variable Declaration Statement","56");
		//ASTClass.instrum("Variable Declaration Statement","52");
		//ASTClass.instrum("Variable Declaration Statement","48");
        sb.append("username='").append(username).append('\'');
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","59");
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","49");
        sb.append(", password='").append(password).append('\'');
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","50");
        sb.append('}');
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","65");
		//ASTClass.instrum("Expression Statement","58");
		//ASTClass.instrum("Expression Statement","51");
        return sb.toString();
    }
}
