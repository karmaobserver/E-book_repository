package makso.rs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.model.Category;
import makso.rs.model.Ebook;
import makso.rs.model.User;
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
	
	public List<Ebook> getEbooksByCategory(long categoryId) {
		System.out.println("Usao u Service" + categoryId);
		ArrayList<Ebook> ebookList = new ArrayList<Ebook>();
		ebookList = (ArrayList<Ebook>) ebookRepository.getEbooksByCategory(categoryId);
		return ebookList;
	}
	
	public Ebook findEbookByFileName(String fileName) {
		Ebook ebook = new Ebook();
		ebook = ebookRepository.findEbookByFileName(fileName);
		return ebook;
	}

	@Override
	public void deleteById(long id) {
		ebookRepository.delete(id);
		
	}

}
