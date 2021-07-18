package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Local;
import service.ServiceManager;

@Path("/local")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocalAPI {
	private final ServiceManager serviceManager = new ServiceManager();
	
	@PUT
	@Path("/")
	public Response putLocal(Local newLocal) {
		return serviceManager.putLocal(newLocal);
	}
	
	@GET
	@Path("{uuid}")
	public Response putLocal(@PathParam("uuid") String uuid) {
		return serviceManager.getLocalById(uuid);
	}

}
