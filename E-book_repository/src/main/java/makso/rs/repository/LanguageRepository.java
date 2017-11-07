package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import makso.rs.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
	
	@Query("SELECT language FROM Language language WHERE language.name = :languageNameLooking") 
	 public Language findLanguageByName(@Param("languageNameLooking") String languageNameLooking);

}
