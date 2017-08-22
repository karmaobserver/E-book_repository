package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.model.Language;
import makso.rs.service.LanguageService;

@RestController
@RequestMapping("/api")
public class LanguageController {
	
	@Autowired
	LanguageService languageService;
	
	@RequestMapping(value = "/languages", method = RequestMethod.GET)
	public ResponseEntity<List<Language>> getLanguages() {
		List<Language> languages = (List<Language>) languageService.getAll();
		return new ResponseEntity<List<Language>>(languages, HttpStatus.OK);
	}

}