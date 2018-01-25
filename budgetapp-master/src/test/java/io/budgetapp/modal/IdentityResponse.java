package io.budgetapp.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityResponse {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		ASTClass.instrum("Expression Statement","18");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IdentityResponse{");
		ASTClass.instrum("Variable Declaration Statement","23");
        sb.append("id=").append(id);
		ASTClass.instrum("Expression Statement","24");
        sb.append('}');
		ASTClass.instrum("Expression Statement","25");
        return sb.toString();
    }
}
