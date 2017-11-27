package makso.rs.service;

import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.dto.CategoryDto;
import makso.rs.dto.CredentialsDto;
import makso.rs.dto.UserAdminDto;
import makso.rs.dto.UserDto;
import makso.rs.model.Category;
import makso.rs.model.Ebook;
import makso.rs.model.User;
import makso.rs.repository.UserRepository;
import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.searcher.ResultRetriever;
import makso.rs.util.StorageService;

@Service
@Transactional
public class UserService implements GenericService<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private StorageService storageService;
	
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User t) {
		return userRepository.save(t);
	}

	@Override
	public User findById(long id) {
		return userRepository.findOne(id);
	}
	
	public User findUserByUsername(String username) {
		User user = new User();
		user = userRepository.findUserByUsername(username);
		return user;
	}
	
	public User passwordExist(String password) {
		return userRepository.passwordExist(password);
	}

	@Override
	public void deleteById(long id) {
		userRepository.delete(id);
		
	}
	
	public ResponseEntity<User> login(CredentialsDto credentialsDto) {  
		System.out.println("username je" + credentialsDto.getUsername());
        System.out.println("password je" + credentialsDto.getPassword());   
        User user = findUserByUsername(credentialsDto.getUsername());
        if (user == null) {
            System.out.println("Unable to find. User with username " + credentialsDto.getUsername() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        
        User userWithPassword = passwordExist(credentialsDto.getPassword());        
        if (userWithPassword == null) {
            System.out.println("Passwords do not match!" + credentialsDto.getUsername());
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        
		return new ResponseEntity<User>(userWithPassword, HttpStatus.OK);
	}
	
	public ResponseEntity<User> updateUser(UserDto userDto) {  
		System.out.println("Updating User " + userDto.getUsername());        
        User currentUser = findUserByUsername(userDto.getUsername());      
        if (currentUser==null) {
            System.out.println("User with username " + userDto.getUsername() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
         
        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());
        currentUser.setPassword(userDto.getPassword());
        save(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	public ResponseEntity<User> editUserByAdmin(UserAdminDto userAdminDto) {  
		System.out.println("Editing User " + userAdminDto.getUsername());
        System.out.println("Category Id is:" + userAdminDto.getCategoryId());
        User user = findById(userAdminDto.getUserId());
        if ((findUserByUsername(userAdminDto.getUsername()) != null) && (!user.getUsername().equals(userAdminDto.getUsername()))) {
            System.out.println("A User with name " + userAdminDto.getUsername() + " already exist");
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        
        if (userAdminDto.getUsername() == null || userAdminDto.getUsername().equals("")) {
        	 System.out.println("A User can't be null!");
        	 return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }
               
        //Index userName in documents
        String oldUserName = user.getUsername();
        QueryParser qp = new QueryParser(Version.LUCENE_4_9,"user",new WhitespaceAnalyzer(Version.LUCENE_4_9));
		List<Document> doc = null;
		try {
			doc = ResultRetriever.getResults(qp.parse("user:" + oldUserName));
			System.out.println(doc.size());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		IndexableField field = new StringField("user", userAdminDto.getUsername(), Store.YES);
		doc.stream().forEach(d -> IndexManager.getIndexer().updateDocument(d, field));
        
		user.setUsername(userAdminDto.getUsername());
        user.setFirstName(userAdminDto.getFirstName());
        user.setLastName(userAdminDto.getLastName());
        user.setPassword(userAdminDto.getPassword());
        user.setUserType(userAdminDto.getUserType());             
        Category userCategory = categoryService.findById(userAdminDto.getCategoryId());     
        user.setCategory(userCategory);       
        save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	public ResponseEntity<User> createUser(UserAdminDto userDto) {  
		System.out.println("Creating User " + userDto.getUsername());	  
        if (findUserByUsername(userDto.getUsername()) != null) {
            System.out.println("A User with name " + userDto.getUsername() + " already exist");
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        
        User user = new User();       
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setUserType(userDto.getUserType());            
        Category userCategory = categoryService.findById(userDto.getCategoryId());     
        user.setCategory(userCategory);     
        save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	public ResponseEntity<User> deleteUser(long userId) {  
		System.out.println("Deleting User with id " + userId);		  
        User user = findById(userId);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        
        //deleting all ebooks which added by that user
        String userName = user.getUsername();
        System.out.println("delete index username: " + userName);
        Query q = new TermQuery(new Term("user", userName));
		List<Document> doc = ResultRetriever.getResults(q);
		IndexManager.getIndexer().deleteDocuments(q);	
		if(!doc.isEmpty()){
			for (Document document : doc) {
				storageService.deleteFile(document);
			}
		}
  
        deleteById(userId);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	public ResponseEntity<User> getUserById(long userId) {  
		System.out.println("Get User with id " + userId);	  
        User user = findById(userId);
        if (user == null) {
            System.out.println("Unable to find user. User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	
	

}
