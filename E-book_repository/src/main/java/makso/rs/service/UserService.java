package makso.rs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.model.User;
import makso.rs.repository.UserRepository;

@Service
@Transactional
public class UserService implements GenericService<User>{

	@Autowired
	private UserRepository userRepository;
	
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

}
