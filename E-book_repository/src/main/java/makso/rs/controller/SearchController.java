package makso.rs.controller;

import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.dto.SearchDto;
import makso.rs.model.Ebook;
import makso.rs.service.SearchService;


@RestController
@RequestMapping("/api")
public class SearchController {
	
private static Log4JLogger logger = new Log4JLogger(SearchController.class.getName());
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Ebook>> searchEbook(@RequestBody SearchDto searchDto) { 
        logger.info(searchDto);       
        return new ResponseEntity<List<Ebook>>(searchService.search(searchDto), HttpStatus.OK);
    }

}
