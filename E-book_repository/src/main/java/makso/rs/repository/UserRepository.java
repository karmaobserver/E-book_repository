package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import makso.rs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}