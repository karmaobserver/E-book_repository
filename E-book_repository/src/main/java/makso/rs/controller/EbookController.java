package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
