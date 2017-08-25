package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.dto.CategoryDto;
import makso.rs.model.Ebook;
import makso.rs.service.EbookService;

@RestController
@RequestMapping("/api")
public class EbookController {
	
	@Autowired
	EbookService ebookService;
	
	@RequestMapping(value = "/ebooks", method = RequestMethod.GET)
	public ResponseEntity<List<Ebook>> getEbooks() {
		List<Ebook> ebooks = (List<Ebook>) ebookService.getAll();
		return new ResponseEntity<List<Ebook>>(ebooks, HttpStatus.OK);
	}
	
	
	 //-------------------Retrieve ebooks by Category--------------------------------------------------------
	
	@RequestMapping(value = "/ebooksByCategory", method = RequestMethod.POST)
	public ResponseEntity<List<Ebook>> getEbooksByCategory(@RequestBody long categoryId) {
		
		System.out.println("Category ID:"+ categoryId);
		List<Ebook> ebooksList = ebookService.getEbooksByCategory(categoryId);
		System.out.println("Lista ebooks:" + ebooksList);
		return new ResponseEntity<List<Ebook>>(ebooksList, HttpStatus.OK);
	}

}
