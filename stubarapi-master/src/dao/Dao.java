package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import model.Critic;
import model.Document;
import model.Institution;
import model.Local;
import model.Offer;
import model.Topic;
import model.User;
import utils.constants.Constants;
import utils.constants.ConstantsAttributes;

public class Dao {
	
	private Connection connection;
	
	public void connection() throws SQLException {
		String url = Constants.CONNECTION;
		String user = Constants.USER;
		String passwd = Constants.PASSWD;
		this.connection = DriverManager.getConnection(url, user, passwd);
	}
	
	public void disconnect() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
	}
	
	//Return institution id by the name given
		public String generateUUID() throws SQLException {
			Statement statement = connection.createStatement();
			statement.executeUpdate(Constants.SET_UUID);
			try (PreparedStatement ps = connection.prepareStatement(Constants.GET_UUID)) {
				try (ResultSet result = ps.executeQuery()) {
					if (result.next()) {
						String uuid = result.getString(ConstantsAttributes.UUID);
						ps.close();
						result.close();
						return uuid;
					}
				}
			}
			return null;
		}
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ INSTITUTION ~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Return institution id by the name given
	public String selectInstitutionIdByName(String name) throws SQLException {
		String insitutionId = null;
		try (PreparedStatement ps = connection.prepareStatement(Constants.GET_INSTITUTION_ID_BY_USERNAME)) {
			ps.setString(1, name);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					insitutionId = result.getString(ConstantsAttributes.ID_INSTITUTION);	
				}
				ps.close();
				result.close();
			}
		}
		return insitutionId;
	}
	
	public ArrayList<Institution> selectAllInstitutions() throws SQLException {
		this.connection();
		ArrayList<Institution> listOfInsitution = new ArrayList<>();
		String select = Constants.GET_INSTITUTION;
		try (Statement statement  = connection.createStatement()) {
			try(ResultSet result = statement.executeQuery(select)) {
				while (result.next()) {
					Institution institution = new Institution();
					institution.setIdInstitution(result.getString(ConstantsAttributes.ID_INSTITUTION));
					institution.setName(result.getString(ConstantsAttributes.NAME));
					institution.setPostcode(result.getInt(ConstantsAttributes.POSTCODE));
					listOfInsitution.add(institution);
				}
				statement.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfInsitution;	
	}   
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ Topic ~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Return topic id by the description given
	public String selectTopicIdByDescription(String description) throws SQLException {
		String topicId = null;
		try (PreparedStatement ps = connection.prepareStatement(Constants.GET_TOPIC_ID_BY_USERNAME)) {
			ps.setString(1, description);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					topicId = result.getString(ConstantsAttributes.ID_TOPIC);
				}
				ps.close();
				result.close();
			}
		}
		return topicId;
	}
	
	public ArrayList<Topic> selectAllTopic() throws SQLException {
		this.connection();
		ArrayList<Topic> listOfTopic = new ArrayList<>();
		String select = Constants.GET_TOPIC;
		try (Statement statement  = connection.createStatement()) {
			try(ResultSet result = statement.executeQuery(select)) {
				while (result.next()) {
					Topic topic = new Topic();
					topic.setIdTopic(result.getString(ConstantsAttributes.ID_TOPIC));
					topic.setDescription(result.getString(ConstantsAttributes.DESCRIPTION));
					listOfTopic.add(topic);
				}
				statement.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfTopic;	
	}   
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ User ~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Return User id by the username given
//	public String selectUserIdByUsername(String username) throws SQLException {
//		String userId = null;
//		try (PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_USER_ID_BY_USERNAME)) {
//			preparedStatement.setString(1, username);
//			try (ResultSet result = preparedStatement.executeQuery()) {
//				if (result.next()) {
//					userId = result.getString(ConstantsAttributes.ID_USER);
//				}
//			}
//		}
//		return userId;
//	}
	
	//Return boolean of the user existence
	public int selectBooleanExistingUserUuid(String userUuid) throws SQLException {
		this.connection();
		String select = Constants.GET_COUNT_USER_UUID;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, userUuid);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					int existingUser = result.getInt(ConstantsAttributes.EXISTING_USER);
					ps.close();
					result.close();
					this.disconnect();
					return existingUser;
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		//return false
		return 0;
	}

	
	//Return boolean of the username existence
	public int selectBooleanExistingUsername(String username) throws SQLException {
		this.connection();
		String select = Constants.GET_COUNT_USERNAME;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, username);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					int existingUser = result.getInt(ConstantsAttributes.EXISTING_USERNAME);
					ps.close();
					result.close();
					this.disconnect();
					return existingUser;
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		//return false
		return 0;
	}
	
	public ArrayList<User> selectAllUsers() throws SQLException {
		this.connection();
		ArrayList<User> listOfUsers = new ArrayList<>();
		String select = Constants.GET_USER;
		try (Statement statement  = connection.createStatement()) {
			try(ResultSet result = statement.executeQuery(select)) {
				while (result.next()) {
					User user = new User();
					user.setUsername(result.getString(ConstantsAttributes.USERNAME));
					user.setName(result.getString(ConstantsAttributes.NAME));
					user.setSurname(result.getString(ConstantsAttributes.SURNAME));
					user.setProfilePhoto(result.getString(ConstantsAttributes.PROFILE_PHOTO));
					user.setEmail(result.getString(ConstantsAttributes.EMAIL));
					user.setBirthday(result.getObject(ConstantsAttributes.BIRTHDAY, LocalDate.class));
					user.setInstitution(result.getString(ConstantsAttributes.PASSWORD));
					listOfUsers.add(user);
				}
				statement.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfUsers;	
	}   
	
	public User selectUserByUuid(String uuid) throws SQLException {
		this.connection();
		User user = null;
		String select = Constants.GET_USER_BY_UUID;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				if (result.next()) {
					user = new User();
					user.setUsername(result.getString(ConstantsAttributes.USERNAME));
					user.setName(result.getString(ConstantsAttributes.NAME));
					user.setSurname(result.getString(ConstantsAttributes.SURNAME));
					user.setProfilePhoto(result.getString(ConstantsAttributes.PROFILE_PHOTO));
					user.setEmail(result.getString(ConstantsAttributes.EMAIL));
					user.setBirthday(result.getObject(ConstantsAttributes.BIRTHDAY,LocalDate.class));
					user.setInstitution(result.getString(ConstantsAttributes.PASSWORD));
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return user;
	}
	
	//Check correct password
		public UUID getUserUUIDByAuthentication(User newUser) throws SQLException {
			this.connection();
			UUID uuid = null;
			String select = Constants.GET_USER_UUID_BY_AUTHENTICATION;
			try (PreparedStatement ps = connection.prepareStatement(select)) {
				ps.setString(1, newUser.getUsername());
				ps.setString(2, newUser.getPassword());
				try (ResultSet result = ps.executeQuery()) {
					if(result.next()) {
						uuid = UUID.fromString(result.getString(ConstantsAttributes.UUID_USER));
					}
					ps.close();
					result.close();
				}
			}
			this.disconnect();
			return uuid;
		}
	
	//insert user
	public void putUser(User newUser) throws SQLException {
		this.connection();
		String insert = Constants.PUT_USER;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newUser.getUsername());
			ps.setString(2,newUser.getPassword());
			ps.setString(3,newUser.getName());
			ps.setString(4,newUser.getSurname());
			ps.setString(5,newUser.getEmail());
			ps.setString(6,newUser.getProfilePhoto());
			ps.setObject(7,newUser.getBirthday());
			ps.setString(8,this.selectInstitutionIdByName(newUser.getInstitution()));
			ps.executeUpdate();
			ps.close();
		}
		this.disconnect();
	}
	
	//insert user
	public void putUserGoogle(User newUser) throws SQLException {
		this.connection();
		String insert = Constants.PUT_USER_GOOGLE;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newUser.getUsername());
			ps.setString(2,newUser.getPassword());
			ps.setString(3,newUser.getName());
			ps.setString(5,newUser.getEmail());
			ps.executeUpdate();
			ps.close();
		}
		this.disconnect();
	}
	
	//Funci�n que actualiza un producto dada su id.
    public void updateUser(User user) throws SQLException {
    	this.connection();
        String update = Constants.UPDATE_USER;
        try (PreparedStatement ps = connection.prepareStatement(update)) { 
			ps.setString(1,user.getPassword());
			ps.setString(2,user.getName());
			ps.setString(3,user.getSurname());
			ps.setString(4,user.getEmail());
			ps.setString(5,user.getProfilePhoto());
			ps.setString(6, user.getIdUser().toString());
        	connection.setAutoCommit(false);
    	  	ps.executeUpdate();
    	  	connection.commit();
        } catch (SQLException ex) {
        	connection.rollback();
        } finally {
        	connection.setAutoCommit(true);
            this.disconnect();
        }
    }
	
  //delete user
  	public void deleteUser(String uuidUser) throws SQLException {
  		this.connection();
  		String insert = Constants.DELETE_USER;
  		try (PreparedStatement ps = connection.prepareStatement(insert)) {
  			ps.setString(1, uuidUser);
  			ps.executeUpdate();
  			ps.close();
  		}
  		this.disconnect();
  	}
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ DOCUMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Return boolean of the document existence
	public int selectBooleanExistingDocumentUuid(String uuid) throws SQLException {
		this.connection();
		String select = Constants.GET_COUNT_DOCUMENT_UUID;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					int existingDocument = result.getInt(ConstantsAttributes.EXISTING_DOCUMENT);
					ps.close();
					result.close();
					this.disconnect();
					return existingDocument;
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		//return false
		return 0;
	}
	    
	public ArrayList<Document> selectAllDocuments() throws SQLException {
		this.connection();
		Document document = null;
		ArrayList<Document> listOfDocuments = new ArrayList<>();
		String select = Constants.GET_DOCUMENT;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					document= new Document(); 
					document.setIdDocument(result.getString(ConstantsAttributes.UUID_DOCUMENT));
					document.setDocPath(result.getString(ConstantsAttributes.DOC_PATH));
					document.setName(result.getString(ConstantsAttributes.NAME));
					document.setRate(result.getDouble(ConstantsAttributes.RATE));
					document.setGrade(result.getInt(ConstantsAttributes.GRADE));
					document.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					document.setTopicName(result.getString(ConstantsAttributes.TOPIC_NAME));
					listOfDocuments.add(document);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfDocuments;
	}
	
	public ArrayList<Document> searchDocumentsByName(String uuid) throws SQLException {
		this.connection();
		Document document = null;
		ArrayList<Document> listOfDocuments = new ArrayList<>();
		String select = Constants.GET_DOCUMENT_SEARCH;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					document= new Document(); 
					document.setIdDocument(result.getString(ConstantsAttributes.UUID_DOCUMENT));
					document.setDocPath(result.getString(ConstantsAttributes.DOC_PATH));
					document.setName(result.getString(ConstantsAttributes.NAME));
					document.setRate(result.getDouble(ConstantsAttributes.RATE));
					document.setGrade(result.getInt(ConstantsAttributes.GRADE));
					document.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					document.setTopicName(result.getString(ConstantsAttributes.TOPIC_NAME));
					listOfDocuments.add(document);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfDocuments;
	}
	
	public Document selectDocumentByUuidDocument(String uuid) throws SQLException {
		this.connection();
		Document document = null;
		String select = Constants.GET_DOCUMENT_BY_UUID_DOCUMENT;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				if (result.next()) {
					document= new Document(); 
					document.setName(result.getString(ConstantsAttributes.NAME));
					document.setDocPath(result.getString(ConstantsAttributes.DOC_PATH));
					document.setRate(result.getDouble(ConstantsAttributes.RATE));
					document.setGrade(result.getInt(ConstantsAttributes.GRADE));
					document.setUserID(result.getString(ConstantsAttributes.UUID_USER));
					document.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					document.setTopicID(result.getString(ConstantsAttributes.ID_TOPIC));
					document.setUsername(result.getString(ConstantsAttributes.USERNAME));
					document.setTopicName(result.getString(ConstantsAttributes.TOPIC_NAME));
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return document;
	}
	

	public ArrayList<Document> selectDocumentsByUuidUser(String uuid) throws SQLException {
		this.connection();
		Document document = null;
		ArrayList<Document> listOfDocuments = new ArrayList<>();
		String select = Constants.GET_DOCUMENTS_BY_UUID_USER;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					document= new Document(); 
					document.setIdDocument(result.getString(ConstantsAttributes.UUID_DOCUMENT));
					document.setDocPath(result.getString(ConstantsAttributes.DOC_PATH));
					document.setName(result.getString(ConstantsAttributes.NAME));
					document.setRate(result.getDouble(ConstantsAttributes.RATE));
					document.setGrade(result.getInt(ConstantsAttributes.GRADE));
					document.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					document.setTopicName(result.getString(ConstantsAttributes.TOPIC_NAME));
					listOfDocuments.add(document);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfDocuments;
	}
	
	public void putDocument(Document newDocument) throws SQLException {
		this.connection();
		String uuid;
		try {
			connection.setAutoCommit(false);
			
			uuid = generateUUID();
			
			insertDocument(uuid, newDocument);
			insertConsultDocument(uuid, newDocument);
			
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
		this.disconnect();
	}
	
	//insert Document
	public void insertDocument(String uuid, Document newDocument) throws SQLException {
		String insert = Constants.PUT_DOCUMENT;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1, uuid);
			ps.setString(2,newDocument.getName());
			ps.setString(3,newDocument.getDocPath());
			ps.setInt(4,newDocument.getGrade());
			ps.setString(5, newDocument.getTopicID().toString());
			ps.setString(6, newDocument.getUserID().toString());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	//update document
	public void updateDocument(Document document) throws SQLException {
    	this.connection();
        String update = Constants.UPDATE_DOCUMENT;
        try (PreparedStatement ps = connection.prepareStatement(update)) { 
			ps.setString(1,document.getName());
			ps.setInt(2,document.getGrade());
			ps.setString(3,document.getTopicID().toString());
			ps.setString(4, document.getIdDocument().toString());
        	connection.setAutoCommit(false);
    	  	ps.executeUpdate();
    	  	connection.commit();
        } catch (SQLException ex) {
        	System.out.println(ex.getMessage());
        	connection.rollback();
        } finally {
        	connection.setAutoCommit(true);
            this.disconnect();
        }
    }
	
	//delete document
  	public void deleteDocument(String uuid) throws SQLException {
  		String insert = Constants.DELETE_DOCUMENT;
  		try (PreparedStatement ps = connection.prepareStatement(insert)) {
  			ps.setString(1, uuid);
  			ps.executeUpdate();
  			ps.close();
  		}
  	}
  	
  	//FULL DELETE DOCUMENT
  	public void fullDeleteDocument(String uuid) throws SQLException {
  		this.connection();
  		try {
			connection.setAutoCommit(false);
			deleteDocumentCritic(uuid);
			deleteConsultDocument(uuid);
			deleteDocument(uuid);

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			this.disconnect();
		}
  	}
		
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONSULT_DOCUMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//insert middle table
	public void insertConsultDocument(String uuid, Document newDocument) throws SQLException {
		String insert = Constants.PUT_CONSULT_DOCUMENT;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newDocument.getUserID().toString());
			ps.setString(2, uuid);
			ps.setObject(3, newDocument.getDate());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	//delete middle table
	public void deleteConsultDocument(String uuid) throws SQLException {
		String delete = Constants.DELETE_CONSULT_DOCUMENT;
		try (PreparedStatement ps = connection.prepareStatement(delete)) {
			ps.setString(1, uuid);
			ps.executeUpdate();
			ps.close();
		}
	}
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOCAL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
//	Return local id by the name given
//	public String selectLocalIdByName(String name) throws SQLException {
//		String userId = null;
//		try (PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_LOCAL_ID_BY_NAME)) {
//			preparedStatement.setString(1, name);
//			try (ResultSet result = preparedStatement.executeQuery()) {
//				if (result.next()) {
//					userId = result.getString(ConstantsAttributes.ID_LOCAL);
//				}
//			}
//		}
//		return userId;
//	}
	
	public Local selectLocalByUuid(String uuid) throws SQLException {
		this.connection();
		Local local = null;
		String select = Constants.GET_LOCAL_BY_UUID;
		
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				if (result.next()) {
					local = new Local();
					local.setName(result.getString(ConstantsAttributes.NAME));
					local.setPostcode(result.getInt(ConstantsAttributes.POSTCODE));
					local.setGeolat(result.getFloat(ConstantsAttributes.GEOLAT));
					local.setGeolong(result.getFloat(ConstantsAttributes.GEOLNG));
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return local;
	}
		
	
	//insert local
	public void putLocal(Local newLocal) throws SQLException {
		this.connection();
		String insert = Constants.PUT_LOCAL;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newLocal.getName());
			ps.setInt(2,newLocal.getPostcode());
			ps.setFloat(3,newLocal.getGeolat());
			ps.setFloat(4, newLocal.getGeolong());
			ps.executeUpdate();
			ps.close();
		}
		this.disconnect();
	}
		
//		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OFFER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Return boolean of the document existence
	public int selectBooleanExistingOfferUuid(String offerUuid) throws SQLException {
		this.connection();
		String select = Constants.GET_COUNT_OFFER_UUID;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, offerUuid);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					int existingOffer = result.getInt(ConstantsAttributes.EXISTING_OFFER);
					ps.close();
					result.close();
					this.disconnect();
					return existingOffer;
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		//return false
		return 0;
	}
		
	public ArrayList<Offer> selectOffersByUuidUser(String uuid) throws SQLException {
		this.connection();
		Offer offer = null;
		ArrayList<Offer> listOfOffers = new ArrayList<>();
		String select = Constants.GET_OFFER_BY_UUID_USER;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					offer = new Offer(); 
					offer.setIdOffer(result.getString(ConstantsAttributes.UUID_OFFER));
					offer.setImageOffer(result.getString(ConstantsAttributes.IMAGE_OFFER));
					offer.setPrice(result.getDouble(ConstantsAttributes.PRICE));
					offer.setComment(result.getString(ConstantsAttributes.COMMENT));
					offer.setLocalName(result.getString(ConstantsAttributes.LOCAL_NAME));
					offer.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					offer.setLocal(result.getString(ConstantsAttributes.ID_LOCAL));
					listOfOffers.add(offer);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfOffers;
	}
	
	public ArrayList<Offer> searchOfferByLocalName(String uuid) throws SQLException {
		this.connection();
		Offer offer = null;
		ArrayList<Offer> listOfOffers = new ArrayList<>();
		String select = Constants.GET_OFFER_SEARCH;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			ps.setString(1, uuid);
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					offer = new Offer(); 
					offer.setIdOffer(result.getString(ConstantsAttributes.UUID_OFFER));
					offer.setImageOffer(result.getString(ConstantsAttributes.IMAGE_OFFER));
					offer.setPrice(result.getDouble(ConstantsAttributes.PRICE));
					offer.setComment(result.getString(ConstantsAttributes.COMMENT));
					offer.setLocalName(result.getString(ConstantsAttributes.LOCAL_NAME));
					offer.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					offer.setLocal(result.getString(ConstantsAttributes.ID_LOCAL));
					listOfOffers.add(offer);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfOffers;
	}
	
	public ArrayList<Offer> selectAllOffers() throws SQLException {
		this.connection();
		Offer offer = null;
		ArrayList<Offer> listOfOffers = new ArrayList<>();
		String select = Constants.GET_OFFER;
		try (PreparedStatement ps = connection.prepareStatement(select)) {
			try (ResultSet result = ps.executeQuery()){
				while (result.next()) {
					offer = new Offer(); 
					offer.setIdOffer(result.getString(ConstantsAttributes.UUID_OFFER));
					offer.setImageOffer(result.getString(ConstantsAttributes.IMAGE_OFFER));
					offer.setPrice(result.getDouble(ConstantsAttributes.PRICE));
					offer.setComment(result.getString(ConstantsAttributes.COMMENT));
					offer.setLocalName(result.getString(ConstantsAttributes.LOCAL_NAME));
					offer.setDate(result.getObject(ConstantsAttributes.DATE, LocalDate.class));
					offer.setLocal(result.getString(ConstantsAttributes.ID_LOCAL));
					listOfOffers.add(offer);
				}
				ps.close();
				result.close();
			}
		}
		this.disconnect();
		return listOfOffers;
	}
	
	//insert Offer and Consult_Offer
	public void putOffer(Offer newOffer) throws SQLException {
		this.connection();
		try {
			connection.setAutoCommit(false);
			
			String uuid = generateUUID();
			
			insertOffer(uuid, newOffer);
			insertConsultOffer(uuid, newOffer);
			
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			this.disconnect();
		}
	}
	
	//insert Offer
	public void insertOffer(String uuid, Offer newOffer) throws SQLException {
		String insert = Constants.PUT_OFFER;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1, uuid);
			ps.setString(2, newOffer.getImageOffer());
			ps.setString(3, newOffer.getComment());
			ps.setDouble(4, newOffer.getPrice());
			ps.setString(5, newOffer.getLocalID().toString());
			ps.setString(6, newOffer.getUserID().toString());
			ps.executeUpdate();
			ps.close();
		}
	}
	

	 public void updateOffer(Offer offer) throws SQLException {
	    	this.connection();
	        String update = Constants.UPDATE_OFFER;
	        try (PreparedStatement ps = connection.prepareStatement(update)) { 
				ps.setString(1,offer.getImageOffer());
				ps.setString(2,offer.getComment());
				ps.setDouble(3,offer.getPrice());
				ps.setString(4, offer.getLocalID().toString());
				ps.setString(5, offer.getIdOffer().toString());
	        	connection.setAutoCommit(false);
	    	  	ps.executeUpdate();
	    	  	connection.commit();
	        } catch (SQLException ex) {
	        	System.out.println(ex.getMessage());
	        	connection.rollback();
	        } finally {
	        	connection.setAutoCommit(true);
	            this.disconnect();
	        }
	    }
	 

	 //delete offer
	  	public void deleteOffer(String uuidOffer) throws SQLException {
	  		String insert = Constants.DELETE_OFFER;
	  		try (PreparedStatement ps = connection.prepareStatement(insert)) {
	  			ps.setString(1, uuidOffer);
	  			ps.executeUpdate();
	  			ps.close();
	  		}
	  	}
	  	
		//FULL DELETE DOCUMENT
	  	public void fullDeleteOffer(String uuid) throws SQLException {
	  		this.connection();
	  		try {
				connection.setAutoCommit(false);
				deleteOfferCritic(uuid);
				deleteConsultOffer(uuid);
				deleteOffer(uuid);

				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			} finally {
				connection.setAutoCommit(true);
				this.disconnect();
			}
	  	}
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONSULT_OFFER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//insert middle table
	public void insertConsultOffer(String uuid, Offer newOffer) throws SQLException {
		String insert = Constants.PUT_CONSULT_OFFER;
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newOffer.getUserID().toString());
			ps.setString(2, uuid);
			ps.setObject(3, newOffer.getDate());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	//delete middle table
	public void deleteConsultOffer(String uuid) throws SQLException {
		String delete = Constants.DELETE_CONSULT_OFFER;
		try (PreparedStatement ps = connection.prepareStatement(delete)) {
			ps.setString(1, uuid);
			ps.executeUpdate();
			ps.close();
		}
	}
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CRITIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//insert Critic
	public void putCritic(Critic newCritic) throws SQLException {
		this.connection();
		String insert;
		
		if (newCritic.getOfferID() != null) {
			insert = Constants.PUT_CRITIC_OFFER; 
		} else {
			insert = Constants.PUT_CRITIC_DOCUMENT; 
		}
		
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1,newCritic.getTitle());
			ps.setString(2, newCritic.getUserID().toString());
			ps.setString(3, newCritic.getComment());
			ps.setObject(4, newCritic.getDate());
			if (newCritic.getDocumentID() != null) {
				ps.setString(5, newCritic.getDocumentID().toString());
			}
			if (newCritic.getOfferID() != null) {
				ps.setString(5, newCritic.getOfferID().toString());
			}
			ps.executeUpdate();
			ps.close();
		}
		this.disconnect();
	}
	
	//delete Document critic 
	public void deleteDocumentCritic(String uuid) throws SQLException {
		String delete = Constants.DELETE_CRITIC_DOCUMENT;
		try(PreparedStatement ps = connection.prepareStatement(delete)) {
			ps.setString(1, uuid);
			ps.executeUpdate();
			ps.close();
		}
	}
	

	//delete Document critic 
	public void deleteOfferCritic(String uuid) throws SQLException {
		String delete = Constants.DELETE_CRITIC_OFFER;
		try(PreparedStatement ps = connection.prepareStatement(delete)) {
			ps.setString(1, uuid);
			ps.executeUpdate();
			ps.close();
		}
	}
	
	
	
	
	
	
	
}