package makso.rs.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import makso.rs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	 @Query("SELECT user FROM User user WHERE user.username = :usernamelooking") 
	 public User findUserByUsername(@Param("usernamelooking") String usernamelooking);
	 
	 @Query("SELECT user FROM User user WHERE user.password = :passwordLooking") 
	 public User passwordExist(@Param("passwordLooking") String passwordLooking);

}