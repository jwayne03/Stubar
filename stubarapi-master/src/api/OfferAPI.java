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

import model.Offer;
import service.ServiceManager;

@Path("/offer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OfferAPI {

	private final ServiceManager serviceManager = new ServiceManager();
	
	@PUT
	@Path("/")
	public Response putOffer(Offer newOffer) {
		return serviceManager.putOffer(newOffer);
	}
	
	@GET
	@Path("/")
	public Response getAllOffer() {
		return serviceManager.getAllOffers();
	}
	
	@POST
	@Path("/modify")
	public Response updateOffer(Offer offer) {
		return serviceManager.updateOffer(offer);
	}
	
	@GET
	@Path("/delete/{uuid}")
	public Response deleteOffer(@PathParam("uuid") String uuid) {
		return serviceManager.deleteOffer(uuid);
	}
	
}
