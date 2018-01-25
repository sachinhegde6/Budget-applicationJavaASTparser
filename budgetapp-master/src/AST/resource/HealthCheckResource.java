package io.budgetapp.resource;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 *
 */
@Path(ResourceURL.HEALTH)
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource extends AbstractResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckResource.class);

    private final HealthCheckRegistry healthCheckRegistry;

    public HealthCheckResource(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","27");
    }

    @Override
    public String getPath() {
        return ResourceURL.HEALTH;
    }

    @GET
    @Path("/ping")
    public Response ping() {
        try {
            Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
			//ASTClass.instrum("Variable Declaration Statement","39");
			//ASTClass.instrum("Variable Declaration Statement","40");
			//ASTClass.instrum("Variable Declaration Statement","41");
			//ASTClass.instrum("Variable Declaration Statement","42");

            for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
                if (!entry.getValue().isHealthy()) {
                    LOGGER.info(entry.getValue().getMessage(), entry.getValue().getError());
					//ASTClass.instrum("Expression Statement","49");
					//ASTClass.instrum("Expression Statement","47");
					//ASTClass.instrum("Expression Statement","45");
					//ASTClass.instrum("Expression Statement","43");
                    return error("error");
                }
				//ASTClass.instrum("If Statement","42");
				//ASTClass.instrum("If Statement","44");
				//ASTClass.instrum("If Statement","46");
				//ASTClass.instrum("If Statement","48");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
			//ASTClass.instrum("Expression Statement","60");
			//ASTClass.instrum("Expression Statement","56");
			//ASTClass.instrum("Expression Statement","52");
			//ASTClass.instrum("Expression Statement","48");
            return error("error");
        }

        return ok("ok");
    }
}
