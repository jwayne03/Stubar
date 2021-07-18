package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Critic;
import service.ServiceManager;

@Path("/critic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CriticAPI {
	private final ServiceManager serviceManager = new ServiceManager();
	
	@PUT
	@Path("/")
	public Response putCritic(Critic newCritic) {
		return serviceManager.putCritic(newCritic);
	}
}
