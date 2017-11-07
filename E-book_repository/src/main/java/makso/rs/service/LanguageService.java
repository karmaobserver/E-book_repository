package makso.rs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.model.Category;
import makso.rs.model.Language;
import makso.rs.repository.LanguageRepository;

@Service
@Transactional
public class LanguageService implements GenericService<Language>{

	@Autowired
	private LanguageRepository languageRepository;
	
	public List<Language> getAll() {
		return languageRepository.findAll();
	}

	@Override
	public Language save(Language t) {
		return languageRepository.save(t);
	}

	@Override
	public Language findById(long id) {
		return languageRepository.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		languageRepository.delete(id);	
	}
	
	public Language findLanguageByName(String name) {
		Language language = new Language();
		language = languageRepository.findLanguageByName(name);
		return language;
	}

}
