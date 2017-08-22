package makso.rs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.model.Ebook;
import makso.rs.repository.EbookRepository;

@Service
@Transactional
public class EbookService implements GenericService<Ebook>{

	@Autowired
	private EbookRepository ebookRepository;
	
	public List<Ebook> getAll() {
		return ebookRepository.findAll();
	}

	@Override
	public Ebook save(Ebook t) {
		return ebookRepository.save(t);
	}

	@Override
	public Ebook findById(long id) {
		return ebookRepository.findOne(id);
	}

}
