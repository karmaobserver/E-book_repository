package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import makso.rs.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	 @Query("SELECT category FROM Category category WHERE category.name = :categoryNameLooking") 
	 public Category findCategoryByName(@Param("categoryNameLooking") String categoryNameLooking);

}

