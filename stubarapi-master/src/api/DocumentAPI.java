package api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import utils.constants.*;

import model.Document;
import service.ServiceManager;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentAPI {
	private final ServiceManager serviceManager = new ServiceManager();
	
	@PUT
	@Path("/")
	public Response putDocument(Document newDocument) {
		return serviceManager.putDocument(newDocument);
	}
	
	@GET
	@Path("{uuid}")
	public Response selectDocumentByIdDocument(@PathParam("uuid") String uuid) {
		return serviceManager.selectDocumentByUuidDocument(uuid);
	}
	
	@GET
	@Path("")
	public Response getAllDocuments() {
		return serviceManager.getAllDocuments();
	}
	
	@POST
	@Path("/modify")
	public Response updateDocument(Document document) {
		return serviceManager.updateDocument(document);
	}
	
	@GET
	@Path("/delete/{uuid}")
	public Response deleteDocument(@PathParam("uuid") String uuid) {
		return serviceManager.deleteDocument(uuid);
	} 
	
//	@POST
//	@Path("/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response uploadFile(@FormDataParam("file") File inputFile) {
//		try {
//			File myObj = new File(Constants.FILE_PATH + inputFile.getName());
//			FileInputStream in = new FileInputStream(inputFile);
//			FileOutputStream out = new FileOutputStream(myObj);
//		
//		
//	      if (myObj.exists()) {
//	    	  int n;
//	    	  
//	    	  while ((n = in.read()) != -1) {
//    			out.write(n); 
//	    	  }
//	    	  
//	    	  if (in != null) { 
//	    		  
//	                // close() function to close the 
//	                // stream 
//	                in.close(); 
//	            } 
//	            // close() function to close 
//	            // the stream 
//	            if (out != null) { 
//	                out.close(); 
//	            } 
//	    	  
//	        return Response.ok(myObj.getName(), MediaType.APPLICATION_JSON).build();
//	      } else {
//	        return Response.ok("File no exists.", MediaType.APPLICATION_JSON).build();
//	      }
//	    } catch (IOException  e) { 
//	      return  Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
//	    }
//	}
	
	@GET
	@Path("/download/{id}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@PathParam("id") String id) {
		return serviceManager.downloadDocument(id);
	}
	
//	@POST
//	@Path("/up")
//	public Response upload64(Document document) {
//			System.out.println(document.getDocPath());
//			String uuidRandom = UUID.randomUUID().toString();
//			String newPath = Constants.FILE_PATH + uuidRandom;
//			BufferedWriter bf = null;
//			
//			//TODO 
//			
//			try {
//				bf = new BufferedWriter(new FileWriter(newPath));
//			} catch (IOException e) {
//				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();	
//			}
//			PrintWriter print = new PrintWriter(bf);
//			
//			print.append(document.getDocPath());
//			
//			return Response.ok(document.getDocPath(), MediaType.APPLICATION_JSON).build();		
//	}
}
