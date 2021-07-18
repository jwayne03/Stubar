package service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;

import java.util.Base64;

import dao.Dao;
import model.*;
import utils.constants.Constants;
import utils.literals.Literals;


public class ServiceManager {
	private Dao dao;
	private Literals literals;
	
	public ServiceManager() {
		this.dao = new Dao();
		this.literals = new Literals();
	}
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INSTITUTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Response getAllInstitutions() {
		try {
			ArrayList<Institution> listOfInstitutions = dao.selectAllInstitutions();
			if (listOfInstitutions.size() > 0) {
				return Response.ok(listOfInstitutions, MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok(literals.EMPTY_USER_LIST, MediaType.APPLICATION_JSON).build();
			}
		} catch (SQLException e) {
			return Response.ok(e.getMessage() , MediaType.APPLICATION_JSON).build();
		}		
	}

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ TOPIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Response getAllTopics() {
		try {
			ArrayList<Topic> listOfTopics = dao.selectAllTopic();
			if (listOfTopics.size() > 0) {
				return Response.ok(listOfTopics, MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok(literals.EMPTY_USER_LIST, MediaType.APPLICATION_JSON).build();
			}
		} catch (SQLException e) {
			return Response.ok(e.getMessage() , MediaType.APPLICATION_JSON).build();
		}		
	}
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ USER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Response getUserUuidByAuthentication(User newUser) {
		try {
			UUID idUser = dao.getUserUUIDByAuthentication(newUser);	
			if (idUser == null) return Response.ok(literals.ERROR_AUTH, MediaType.APPLICATION_JSON).build();
			return Response.ok(String.format(literals.USER_AUTH, idUser), MediaType.APPLICATION_JSON).build();	
		} catch (SQLException e) {
			return Response.ok(literals.ERROR_AUTH, MediaType.APPLICATION_JSON).build();
		}	
	}
	
	public Response getAllUsers() {
		try {
			ArrayList<User> listOfUsers = dao.selectAllUsers();
			if (listOfUsers.size() > 0) {
				return Response.ok(listOfUsers, MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok(literals.EMPTY_USER_LIST, MediaType.APPLICATION_JSON).build();
			}
		} catch (SQLException e) {
			return Response.ok(e.getMessage() , MediaType.APPLICATION_JSON).build();
		}		
	}
	
	public Response getUserByUuid(String uuid) {
		try {
			User user = dao.selectUserByUuid(uuid);
			if (user != null) {
				return Response.ok(user, MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok(literals.NO_EXISTING_USER, MediaType.APPLICATION_JSON).build();
			}
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response putUser(User newUser) {
		try {
			if (dao.selectBooleanExistingUsername(newUser.getUsername()) != 0) {
				return Response.ok(literals.USERNAME_ALREDY_EXISTS, MediaType.APPLICATION_JSON).build();
			} else if(newUser.getBirthday() == null && newUser.getInstitution() == null && newUser.getProfilePhoto() == null) {
				dao.putUserGoogle(newUser);
				return Response.ok(literals.USER_CREATED, MediaType.APPLICATION_JSON).build();
			}
			dao.putUser(newUser);
			return Response.ok(literals.USER_CREATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	

	public Response updateUser(User user) {
		try {
			if (dao.selectBooleanExistingUserUuid(user.getIdUser().toString()) == 0) {
				return Response.ok(literals.NO_EXISTING_USER, MediaType.APPLICATION_JSON).build();
			}
			
			String uuidImg = UUID.randomUUID().toString();
			System.out.print(uuidImg.toString());
			String uuidUser = user.getIdUser().toString();
			String base64File = user.getProfilePhoto();
			
			System.out.println(uuidImg);
			user.setProfilePhoto(uuidImg);
			System.out.print(user.getProfilePhoto());
			
			File directory = new File(Constants.FILE_PATH + uuidUser);
			if (!directory.exists()) {
				directory.mkdir();
			}
			
			String newPath = Constants.FILE_PATH + uuidUser + "/" + uuidImg;
			BufferedWriter bf = new BufferedWriter(new FileWriter(newPath));
			PrintWriter print = new PrintWriter(bf);
			
			print.append(base64File);
			
			bf.flush();
			bf.close();
			print.close();
			
			dao.updateUser(user);
			return Response.ok(literals.USER_UPDATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException | IOException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response downloadProfilePicture(String uuid) {
		try {
			User user = dao.selectUserByUuid(uuid);
			BufferedReader bf = new BufferedReader(new FileReader(Constants.FILE_PATH + uuid + "/" + user.getProfilePhoto()));
			
			StringBuilder documentBase64 = new StringBuilder();
			String read;
			while ((read = bf.readLine()) != null) {
				documentBase64.append(read);
			}

			byte[] decoded = Base64.getDecoder().decode(documentBase64.toString());
			FileUtils.writeByteArrayToFile(new File("/tmp/test.png"), decoded);
		
		     return Response.ok(new File("/tmp/test.png"), "image/png").build();
		} catch (SQLException | IOException e) {
			return Response.status(404).build();
		}
	}

	public Response deleteUser(String uuidUser) {
		try {
			if (dao.selectBooleanExistingUserUuid(uuidUser) == 0) {
				return Response.ok(literals.NO_EXISTING_USER, MediaType.APPLICATION_JSON).build();
			}
			dao.deleteUser(uuidUser);
			return Response.ok(literals.USER_DELETED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DOCUMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public Response putDocument(Document newDocument) {
		try {
			String uuidFile = UUID.randomUUID().toString();
			String uuidUser = newDocument.getUserID().toString();
			String base64File = newDocument.getDocPath();
			
			newDocument.setDocPath(uuidFile);
			dao.putDocument(newDocument);	
			
			File directory = new File(Constants.FILE_PATH + uuidUser);
			if (!directory.exists()) {
				directory.mkdir();
			}
			
			String newPath = Constants.FILE_PATH + uuidUser + "/" + uuidFile;
			BufferedWriter bf = new BufferedWriter(new FileWriter(newPath));
			PrintWriter print = new PrintWriter(bf);
			
			print.append(base64File);
			
			bf.flush();
			bf.close();
			print.close();
			
			return Response.ok(literals.DOCUMENT_CREATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException | IOException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response downloadDocument(String uuid) {
		try {
			Document document = dao.selectDocumentByUuidDocument(uuid);
			BufferedReader bf = new BufferedReader(new FileReader(Constants.FILE_PATH + document.getUserID() + "/" + document.getDocPath()));
			
			StringBuilder documentBase64 = new StringBuilder();
			String read;
			while ((read = bf.readLine()) != null) {
				documentBase64.append(read);
			}

			byte[] decoded = Base64.getDecoder().decode(documentBase64.toString());
			 FileOutputStream fos = new FileOutputStream("/tmp/temporary.pdf");
			 
		     fos.write(decoded);
		     bf.close();
		     fos.flush();
		     fos.close();
		        			
		     return Response.ok(new File("/tmp/temporary.pdf")).type("application/pdf").build();
		} catch (SQLException | IOException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response selectDocumentByUuidDocument(String uuid) {
		try {
			Document document = dao.selectDocumentByUuidDocument(uuid);
			return Response.ok(document, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response searchDocumentByName(String uuid) {
		try {
			ArrayList<Document> listOfDocuments = dao.searchDocumentsByName(uuid);
			return Response.ok(listOfDocuments, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	

	public Response getDocumentsByUuidUser(String uuid) {
		try {
			ArrayList<Document> listOfDocuments = dao.selectDocumentsByUuidUser(uuid);
			return Response.ok(listOfDocuments, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}

	public Response getAllDocuments() {
		try {
			ArrayList<Document> listOfDocuments = dao.selectAllDocuments();
			return Response.ok(listOfDocuments, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}

	public Response updateDocument(Document document) {
		try {
			if (dao.selectBooleanExistingDocumentUuid(document.getIdDocument().toString()) == 0) {
				return Response.ok(literals.NO_EXISTING_DOCUMENT, MediaType.APPLICATION_JSON).build();
			}
			dao.updateDocument(document);
			return Response.ok(literals.DOCUMENT_UPDATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}

	public Response deleteDocument(String uuid) {
		try {
			System.err.println(uuid);
			if (dao.selectBooleanExistingDocumentUuid(uuid) == 0) {
				return Response.ok(literals.NO_EXISTING_DOCUMENT, MediaType.APPLICATION_JSON).build();
			}
			dao.fullDeleteDocument(uuid);
			return Response.ok(literals.DOCUMENT_DELETED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOCAL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public Response putLocal(Local newLocal) {
		try {
			dao.putLocal(newLocal);
			return Response.ok(literals.LOCAL_CREATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response getLocalById(String uuid) {
		try {
			Local local = dao.selectLocalByUuid(uuid);
			return Response.ok(local, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OFFER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public Response putOffer(Offer newOffer) {
		try {
			dao.putOffer(newOffer);
			return Response.ok(literals.OFFER_CREATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response getOffersByIdUser(String id) {
		try {
			ArrayList<Offer> listOfOffers = dao.selectOffersByUuidUser(id);
			return Response.ok(listOfOffers, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public Response searchOfferByLocalName(String id) {
		try {
			ArrayList<Offer> listOfOffers = dao.searchOfferByLocalName(id);
			return Response.ok(listOfOffers, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	

	public Response getAllOffers() {
		try {
			ArrayList<Offer> listOfOffers = dao.selectAllOffers();
			return Response.ok(listOfOffers, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
	

	public Response updateOffer(Offer offer) {
		try {
			if (dao.selectBooleanExistingOfferUuid(offer.getIdOffer().toString()) == 0) {
				return Response.ok(literals.NO_EXISTING_OFFER, MediaType.APPLICATION_JSON).build();
			}
			dao.updateOffer(offer);
			return Response.ok(literals.OFFER_UPDATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}


	public Response deleteOffer(String uuidOffer) {
		try {
			if (dao.selectBooleanExistingOfferUuid(uuidOffer) == 0) {
				return Response.ok(literals.NO_EXISTING_OFFER, MediaType.APPLICATION_JSON).build();
			}
			dao.fullDeleteOffer(uuidOffer);
			return Response.ok(literals.OFFER_DELETED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CRITIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public Response putCritic(Critic newCritic) {
		try {
			dao.putCritic(newCritic);
			return Response.ok(literals.CRITIC_CREATED, MediaType.APPLICATION_JSON).build();
		} catch (SQLException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}
}

