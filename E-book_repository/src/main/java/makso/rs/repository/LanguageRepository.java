package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import makso.rs.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

}
