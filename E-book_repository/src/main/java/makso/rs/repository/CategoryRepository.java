package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import makso.rs.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

