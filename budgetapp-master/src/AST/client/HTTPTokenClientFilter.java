package io.budgetapp.client;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 *
 */
public class HTTPTokenClientFilter implements ClientRequestFilter {

    private final String token;

    public HTTPTokenClientFilter(String token) {
        this.token = token;
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
		//ASTClass.instrum("Expression Statement","16");
    }

    @Override
    public void filter(ClientRequestContext crc) throws IOException {
        crc.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		//ASTClass.instrum("Expression Statement","token,","Bearer " + token","21");
		//ASTClass.instrum("Expression Statement","token,","Bearer " + token","22");
		//ASTClass.instrum("Expression Statement","token,","Bearer " + token","23");
		//ASTClass.instrum("Expression Statement","token,","Bearer " + token","24");
    }
}
