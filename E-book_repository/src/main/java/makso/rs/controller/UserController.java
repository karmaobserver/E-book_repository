package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
