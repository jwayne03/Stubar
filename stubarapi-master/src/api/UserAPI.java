package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;
import service.ServiceManager;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAPI {
	private final ServiceManager serviceManager = new ServiceManager();
	
	@GET
	@Path("/")
	public Response selectAllUser() {
		return serviceManager.getAllUsers();
	}
	
	@GET
	@Path("{uuid}")
	public Response selectUserByUuid(@PathParam("uuid") String uuid) {
		return serviceManager.getUserByUuid(uuid);
	}
	
	@Produces("image/png")
	@GET
	@Path("{uuid}/profilePhoto")
	public Response downloadPhofilePhoto(@PathParam("uuid") String uuid) {
		return serviceManager.downloadProfilePicture(uuid);
	}
	
	@PUT
	@Path("/")
	public Response putUser(User newUser) {
		return serviceManager.putUser(newUser);
	}
	
	@POST
	@Path("/authentication")
	public Response selectUserUuidByAutenthentication(User newUser) {
		return serviceManager.getUserUuidByAuthentication(newUser);
	}
	
	@POST
	@Path("/modify")
	public Response updateUser(User user) {
		return serviceManager.updateUser(user);
	}
	
	@GET
	@Path("/documents/{uuid}")
	public Response selectDocumentsByUuidUser(@PathParam("uuid") String uuid) {
		return serviceManager.getDocumentsByUuidUser(uuid);
	}

	@GET
	@Path("/offers/{uuid}")
	public Response selectOffersByUuidUser(@PathParam("uuid") String uuid) {
		return serviceManager.getOffersByIdUser(uuid);
	}
	
	@GET
	@Path("/delete/{uuid}")
	public Response deleteUser(@PathParam("uuid") String uuid) {
		return serviceManager.deleteUser(uuid);
	}
}
