package utils.constants;

public class Constants {
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ FILE ~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String FILE_PATH = "/stubarFiles/";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ BBDD ~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String CONNECTION = "jdbc:mysql://localhost:3306/stubar?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USER = "root";
	public static final String PASSWD = "Stubar.2020";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ General ~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String SET_UUID = "SET @uuid = uuid();";
	public static final String GET_UUID = "select @uuid as uuid;";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~ USER ~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_USER_ID_BY_USERNAME = "select bin_to_uuid(id_user) as id_User from `user` where username = (?)";
	public static final String GET_USER_UUID_BY_AUTHENTICATION = "select bin_to_uuid(id_User) as id_User from user where username = (?) and password = (?)";
	public static final String GET_USER  = "select * from user";
	public static final String GET_USER_BY_UUID  = "select id_User, username, password, name, email, surname, bin_to_uuid(profile_Photo) as profile_Photo, birthday, bin_to_uuid(id_Institution) from user where id_User = uuid_to_bin(?)";
	public static final String GET_COUNT_USERNAME = "select count(username) as existing_username from `user` where username = (?)";
	public static final String GET_COUNT_USER_UUID = "select count(id_User) as existing_user from `user` where id_User = uuid_to_bin(?)";
	public static final String PUT_USER = "insert into `user` values (uuid_to_bin(uuid()),?,?,?,?,?,uuid_to_bin(?),?,uuid_to_bin(?))";
	public static final String PUT_USER_GOOGLE = "insert into `user` (id_User, name, username, password, email) values(UUID_TO_BIN(UUID()),?,?,?,?)";
	public static final String UPDATE_USER = "update `user` set password=(?), name=(?), surname=(?), email=(?), profile_Photo=uuid_to_bin(?) where id_User = uuid_to_bin((?))";
	public static final String DELETE_USER = "delete from `user` where id_User = uuid_to_bin(?)";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INSTITUTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_INSTITUTION_ID_BY_USERNAME = "select bin_to_uuid(id_Institution) as id_Institution from institution where name = (?)";
	public static final String GET_INSTITUTION  = "select bin_to_uuid(id_Institution) as id_Institution, name, postcode from institution order by name asc";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ TOPIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_TOPIC_ID_BY_USERNAME = "select bin_to_uuid(id_Topic) as id_Topic from topic where description = (?)";
	public static final String GET_TOPIC = "select bin_to_uuid(id_Topic) as id_Topic, description from topic";

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOCAL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_LOCAL  = "select * from local";
	public static final String GET_LOCAL_BY_UUID  = "select bin_to_uuid(id_Local) as id_Local, name, postcode, geolat, geolng from local where id_Local = uuid_to_bin(?)";
	public static final String GET_LOCAL_ID_BY_NAME = "select bin_to_uuid(id_Local) as id_Local from `local` where name = (?)";
	public static final String PUT_LOCAL = "insert into local values (uuid_to_bin(uuid()), ?,?,?,?)";
	
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OFFER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_OFFER  = "select bin_to_uuid(o.id_Offer) as id_Offer, o.image_Offer, o.price, o.comment, l.name as local_Name, co.date, bin_to_uuid(o.id_Local) as id_Local"
														+ " from offer o"
														+ " left join user u on o.id_User = u.id_User"
														+ " left join local l on o.id_Local = l.id_Local"
														+ " left join consult_offer co on o.id_Offer = co.id_Offer";
	public static final String GET_OFFER_SEARCH  = "select bin_to_uuid(o.id_Offer) as id_Offer, o.image_Offer, o.price, o.comment, l.name as local_Name, co.date, bin_to_uuid(o.id_Local) as id_Local"
			+ " from offer o"
			+ " left join user u on o.id_User = u.id_User"
			+ " left join local l on o.id_Local = l.id_Local"
			+ " left join consult_offer co on o.id_Offer = co.id_Offer"
			+ " where l.name like '%?%' ";
	public static final String GET_OFFER_BY_ID  = "select * from offer where id_Offer = uuid_to_bin(?)";
	public static final String PUT_OFFER = "insert into offer values (uuid_to_bin(?), ?,?,?, uuid_to_bin(?), uuid_to_bin(?))";
	public static final String GET_OFFER_BY_UUID_USER = "select bin_to_uuid(o.id_Offer) as id_Offer, o.image_Offer, o.price, o.comment, l.name as local_Name, co.date, bin_to_uuid(o.id_Local) as id_Local"
											+ " from offer o"
											+ " left join user u on o.id_User = u.id_User"
											+ " left join local l on o.id_Local = l.id_Local"
											+ " left join consult_offer co on o.id_Offer = co.id_Offer"
											+ " where bin_to_uuid(o.id_User) = ?";	
	public static final String UPDATE_OFFER = "update offer set image_Offer = (?), comment = (?), price = (?), id_Local = uuid_to_bin(?) where id_Offer = uuid_to_bin(?)";
	public static final String GET_COUNT_OFFER_UUID = "select count(id_Offer) as existing_offer from `offer` where id_Offer = uuid_to_bin(?)";
	public static final String DELETE_OFFER = "delete from `offer` where id_Offer = uuid_to_bin(?)";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DOCUMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_DOCUMENT  = "select bin_to_uuid(d.id_Document) as id_Document, d.name, bin_to_uuid(d.doc_Path) as doc_Path, d.rate, cd.date, d.grade, bin_to_uuid(d.id_User) as id_User, bin_to_uuid(d.id_Topic) as id_Topic, u.username as username, t.description as topic_Name from document d"
											+ " left join user u on d.id_User = u.id_User"
											+ " left join topic t on d.id_Topic = t.id_Topic"
											+ " left join consult_document cd on d.id_Document = cd.id_Document";
	public static final String GET_DOCUMENT_SEARCH  = "select bin_to_uuid(d.id_Document) as id_Document, d.name, bin_to_uuid(d.doc_Path) as doc_Path, d.rate, cd.date, d.grade, bin_to_uuid(d.id_User) as id_User, bin_to_uuid(d.id_Topic) as id_Topic, u.username as username, t.description as topic_Name from document d"
			+ " left join user u on d.id_User = u.id_User"
			+ " left join topic t on d.id_Topic = t.id_Topic"
			+ " left join consult_document cd on d.id_Document = cd.id_Document"
			+ " where d.name like '%?%'";
	public static final String GET_DOCUMENT_BY_UUID_DOCUMENT  = "select d.name, bin_to_uuid(d.doc_Path) as doc_Path, d.rate, cd.date, d.grade,bin_to_uuid(d.id_User) as id_User, bin_to_uuid(d.id_Topic) as id_Topic, u.username as username, t.description as topic_Name "
														+ " from document d"
														+ " left join user u on d.id_User = u.id_User"
														+ " left join topic t on d.id_Topic = t.id_Topic"
														+ " left join consult_document cd on d.id_Document = cd.id_Document"
														+ " where bin_to_uuid(d.id_Document) = ?";
	public static final String GET_DOCUMENTS_BY_UUID_USER = "select bin_to_uuid(d.id_Document) as id_Document, d.name, d.doc_path, d.rate, cd.date, d.grade, t.description as topic_Name from document d"
															+ " left join user u on d.id_User = u.id_User"
															+ " left join topic t on d.id_Topic = t.id_Topic"
															+ " left join consult_document cd on d.id_Document = cd.id_Document"
															+ " where bin_to_uuid(d.id_User) = ?";
	public static final String PUT_DOCUMENT = "insert into document values (uuid_to_bin(?),?,uuid_to_bin(?),0,?,uuid_to_bin(?),uuid_to_bin(?))";
	public static final String UPDATE_DOCUMENT = "update document set name = (?), grade = (?), id_Topic = uuid_to_bin(?) where id_Document = uuid_to_bin(?)";
	public static final String GET_COUNT_DOCUMENT_UUID = "select count(id_Document) as existing_document from `document` where id_Document = uuid_to_bin(?)";
	public static final String DELETE_DOCUMENT = "delete from `document` where id_Document = uuid_to_bin(?)";

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CRITIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String GET_CRITIC_BY_ID  = "select * from critic where id_Critic = uuid_to_bin(?)";
	public static final String PUT_CRITIC_DOCUMENT  = "insert into critic values (uuid_to_bin(uuid()),?,uuid_to_bin(?),?,0,?,uuid_to_bin(?),null)";
	public static final String PUT_CRITIC_OFFER= "insert into critic values (uuid_to_bin(uuid()),?,uuid_to_bin(?),?,0,?, null, uuid_to_bin(?))";
	public static final String DELETE_CRITIC_DOCUMENT = "delete from critic where id_Document =  uuid_to_bin(?) order by id_Document desc";
	public static final String DELETE_CRITIC_OFFER = "delete from critic where id_Offer =  uuid_to_bin(?) order by id_Offer desc";
	
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONSULT_DOCUMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String PUT_CONSULT_DOCUMENT = "insert into consult_document values (uuid_to_bin(?), uuid_to_bin(?),?)";
	public static final String DELETE_CONSULT_DOCUMENT = "delete from `consult_document` where id_Document = uuid_to_bin(?)";

//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONSULT_OFFER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String PUT_CONSULT_OFFER = "insert into consult_offer values (uuid_to_bin(?), uuid_to_bin(?),?)";
	public static final String DELETE_CONSULT_OFFER = "delete from `consult_offer` where id_Offer = uuid_to_bin(?)";
}
