package makso.rs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import makso.rs.model.Ebook;
import makso.rs.model.User;

@Repository
public interface EbookRepository extends JpaRepository<Ebook, Long> {
	
	
	@Query("SELECT ebook FROM Ebook ebook WHERE ebook.category.id = :categoryIdLooking") 
	public  List<Ebook> getEbooksByCategory(@Param("categoryIdLooking") long categoryIdLooking);
	
	
	@Query("SELECT ebook FROM Ebook ebook WHERE ebook.fileName = :fileNameLooking") 
	public Ebook findEbookByFileName(@Param("fileNameLooking") String fileNameLooking);

}

