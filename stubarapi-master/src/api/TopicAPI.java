package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.ServiceManager;

@Path("/topic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TopicAPI {

	private final ServiceManager serviceManager = new ServiceManager();

	@GET
	@Path("/")
	public Response getAllTopics() {
		return serviceManager.getAllTopics();
	}
}
