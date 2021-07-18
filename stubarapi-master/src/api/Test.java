package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Test {

	@GET
	@Path("/")
	public Response welcomeParty() {
		return Response.ok("Welcome to the apitest", MediaType.APPLICATION_JSON).build();
	}
}
