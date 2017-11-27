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

	//-------------------List all Users--------------------------------------------------------
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = (List<User>) userService.getAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	//-------------------Retrieve single User--------------------------------------------------------
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody CredentialsDto credentialsDto) {
        ResponseEntity<User> responseEntity = userService.login(credentialsDto);
        return responseEntity;
    }
     
    //------------------- Update a User --------------------------------------------------------
    
    @RequestMapping(value = "/editUser", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
    	ResponseEntity<User> responseEntity = userService.updateUser(userDto);
        return responseEntity;
    }
    
    //-------------------Edit a user by Admin--------------------------------------------------------
    
    @RequestMapping(value = "/editUserByAdmin", method = RequestMethod.PUT)
    public ResponseEntity<User> editUserByAdmin(@RequestBody UserAdminDto userAdminDto) {       
    	ResponseEntity<User> responseEntity = userService.editUserByAdmin(userAdminDto);
        return responseEntity;
    }
      
    //-------------------Create a User--------------------------------------------------------
    
    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserAdminDto userDto) {
    	ResponseEntity<User> responseEntity = userService.createUser(userDto);
        return responseEntity;
    }
    
    //------------------- Delete a User --------------------------------------------------------
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestBody long userId) {
    	ResponseEntity<User> responseEntity = userService.deleteUser(userId);
        return responseEntity;
    }
    
    //------------------- Get user by ID --------------------------------------------------------
    
    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public ResponseEntity<User> getUserById(@RequestBody long userId) {       
    	ResponseEntity<User> responseEntity = userService.getUserById(userId);
        return responseEntity;
    }

}
