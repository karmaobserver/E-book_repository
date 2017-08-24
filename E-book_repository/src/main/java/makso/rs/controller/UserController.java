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
import makso.rs.dto.UserDto;
import makso.rs.model.User;
import makso.rs.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
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

}
