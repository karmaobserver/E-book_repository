package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.dto.CredentialsDto;
import makso.rs.dto.UserAdminDto;
import makso.rs.dto.UserDto;
import makso.rs.model.Category;
import makso.rs.model.User;
import makso.rs.service.CategoryService;
import makso.rs.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = (List<User>) userService.getAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	 //-------------------Retrieve Single User--------------------------------------------------------
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody CredentialsDto credentialsDto) {
    	System.out.println("dto je" + credentialsDto);
        System.out.println("username je" + credentialsDto.getUsername());
        System.out.println("password je" + credentialsDto.getPassword());
        
        User user = userService.findUserByUsername(credentialsDto.getUsername());
        
        if (user == null) {
            System.out.println("Unable to find. User with username " + credentialsDto.getUsername() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        
        User userWithPassword = userService.passwordExist(credentialsDto.getPassword());
        
        if (userWithPassword == null) {
            System.out.println("Passwords do not match!" + credentialsDto.getUsername());
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        
        System.out.println(userWithPassword.getUsername());
        return new ResponseEntity<User>(userWithPassword, HttpStatus.OK);
    }
    
    
    //------------------- Update a User --------------------------------------------------------
    
    @RequestMapping(value = "/editUser", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        System.out.println("Updating User " + userDto.getUsername());
              
        User currentUser = userService.findUserByUsername(userDto.getUsername());
          
        if (currentUser==null) {
            System.out.println("User with username " + userDto.getUsername() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());
        currentUser.setPassword(userDto.getPassword());
          
  
        userService.save(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    
    //-------------------Edit a Use by Admin--------------------------------------------------------
    
    @RequestMapping(value = "/editUserByAdmin", method = RequestMethod.PUT)
    public ResponseEntity<User> editUserByAdmin(@RequestBody UserAdminDto userAdminDto) {
        System.out.println("Editing User " + userAdminDto.getUsername());
        System.out.println("Kategorija ID je:" + userAdminDto.getCategoryId());
        
        
        if (userService.findUserByUsername(userAdminDto.getUsername()) != null) {
            System.out.println("A User with name " + userAdminDto.getUsername() + " already exist");
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        
        if (userAdminDto.getUsername() == null || userAdminDto.getUsername().equals("")) {
        	 System.out.println("A User can't be null!");
        	 return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }
        
        User user = userService.findById(userAdminDto.getUserId());
        
        if (!userAdminDto.getUsername().equals("sameUsername")) {
        	System.out.println("Username is same as old one");
        	user.setUsername(userAdminDto.getUsername());
        }
       
        user.setFirstName(userAdminDto.getFirstName());
        user.setLastName(userAdminDto.getLastName());
        user.setPassword(userAdminDto.getPassword());
        user.setUserType(userAdminDto.getUserType());
             
        Category userCategory = categoryService.findById(userAdminDto.getCategoryId());
      
        user.setCategory(userCategory);
        
        userService.save(user);
  
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    
    //-------------------Create a User--------------------------------------------------------
    
    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserAdminDto userDto) {
        System.out.println("Creating User " + userDto.getUsername());
  
        if (userService.findUserByUsername(userDto.getUsername()) != null) {
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
        
        userService.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
    
    
    //------------------- Delete a User --------------------------------------------------------
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestBody long userId) {
        System.out.println("Deleting User with id " + userId);
  
        User user = userService.findById(userId);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteById(userId);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
