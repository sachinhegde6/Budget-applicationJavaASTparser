package io.budgetapp.resource;

import io.budgetapp.model.Point;
import io.budgetapp.model.Transaction;
import io.budgetapp.model.User;
import io.budgetapp.model.form.TransactionForm;
import io.budgetapp.service.FinanceService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 */
@Path(ResourceURL.TRANSACTION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource extends AbstractResource {

    private final FinanceService financeService;

    public TransactionResource(FinanceService financeService) {
        this.financeService = financeService;
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
    }

    @Override
    public String getPath() {
        return ResourceURL.TRANSACTION;
    }

    @GET
    @UnitOfWork
    public List<Transaction> findAllTransactions(@Auth User user, @QueryParam("limit") Integer limit) {
        return financeService.findRecentTransactions(user, limit);
    }

    @POST
    @UnitOfWork
    public Response add(@Auth User user, TransactionForm transactionForm) {
        Transaction transaction = financeService.addTransaction(user, transactionForm);
		//ASTClass.instrum("Variable Declaration Statement","47");
		//ASTClass.instrum("Variable Declaration Statement","46");
		//ASTClass.instrum("Variable Declaration Statement","45");
		//ASTClass.instrum("Variable Declaration Statement","44");
        return created(transaction, transaction.getId());
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Transaction findById(@PathParam("id") long id) {
        return financeService.findTransactionById(id);
    }

    @DELETE
    @UnitOfWork
    @Path("/{id}")
    public Response delete(@Auth User user, @PathParam("id") long id) {
        boolean deleted = financeService.deleteTransaction(user, id);
		//ASTClass.instrum("Variable Declaration Statement","65");
		//ASTClass.instrum("Variable Declaration Statement","63");
		//ASTClass.instrum("Variable Declaration Statement","61");
		//ASTClass.instrum("Variable Declaration Statement","59");
        if(deleted) {
            return deleted();
        } else {
            return notFound();
        }
		//ASTClass.instrum("If Statement","60");
		//ASTClass.instrum("If Statement","63");
		//ASTClass.instrum("If Statement","66");
		//ASTClass.instrum("If Statement","69");
    }

    @GET
    @UnitOfWork
    @Path("/summary")
    public List<Point> findSummary(@Auth User user, @QueryParam("month") Integer month, @QueryParam("year") Integer year) {
        return financeService.findTransactionUsage(user, month, year);
    }

    @GET
    @UnitOfWork
    @Path("/monthly")
    public List<Point> findMonthly(@Auth User user) {
        return financeService.findMonthlyTransactionUsage(user);
    }

    @GET
    @UnitOfWork
    @Path("/today")
    public Response findTodayRecurringTransactions(@Auth User user) {
        return ok(financeService.findTodayRecurringsTransactions(user));
    }

}
