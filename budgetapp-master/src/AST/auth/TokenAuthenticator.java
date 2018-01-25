package io.budgetapp.auth;

import io.budgetapp.model.User;
import io.budgetapp.service.FinanceService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

/**
 *
 */
public class TokenAuthenticator implements Authenticator<String, User> {

    private final FinanceService financeService;

    public TokenAuthenticator(FinanceService financeService) {
        this.financeService = financeService;
		//ASTClass.instrum("Expression Statement","19");
		//ASTClass.instrum("Expression Statement","19");
		//ASTClass.instrum("Expression Statement","19");
		//ASTClass.instrum("Expression Statement","19");
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return financeService.findUserByToken(token);
    }
}
