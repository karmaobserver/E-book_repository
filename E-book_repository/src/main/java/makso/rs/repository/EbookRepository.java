package makso.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import makso.rs.model.Ebook;

@Repository
public interface EbookRepository extends JpaRepository<Ebook, Long> {

}

