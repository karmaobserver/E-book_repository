package makso.rs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javassist.NotFoundException;
import makso.rs.dto.CategoryDto;
import makso.rs.dto.EbookAddDto;
import makso.rs.dto.EbookDto;
import makso.rs.dto.EbookViewModel;
import makso.rs.dto.UserAdminDto;
import makso.rs.model.Category;
import makso.rs.model.Ebook;
import makso.rs.model.Language;
import makso.rs.model.User;
import makso.rs.service.CategoryService;
import makso.rs.service.EbookService;
import makso.rs.service.LanguageService;
import makso.rs.service.UserService;
import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.indexer.UDDIndexer;
import makso.rs.udd.searcher.InformationRetriever;
import makso.rs.udd.searcher.ResultRetriever;
import makso.rs.util.EbookPDFHandler;
import makso.rs.util.StorageService;

@RestController
@RequestMapping("/api")
public class EbookController {
	
	@Autowired
	EbookService ebookService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LanguageService languageService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	StorageService storageService;
	
	//-------------------List all ebooks--------------------------------------------------------
	
	@RequestMapping(value = "/ebooks", method = RequestMethod.GET)
	public ResponseEntity<List<Ebook>> getEbooks() {
		List<Ebook> ebooks = ebookService.getAll();
		return new ResponseEntity<List<Ebook>>(ebooks, HttpStatus.OK);
		
	}
	
	//-------------------Retrieve ebooks by Category--------------------------------------------------------
	
	@RequestMapping(value = "/ebooksByCategory", method = RequestMethod.POST)
	public ResponseEntity<List<Ebook>> getEbooksByCategory(@RequestBody Long categoryId) {		
		List<Ebook> ebooks = ebookService.getEbooksByCategory(categoryId);	
		return new ResponseEntity<List<Ebook>>(ebooks, HttpStatus.OK);
	}
	
	//-------------------Get ebook data based on ebook id--------------------------------------------------------
	
	@RequestMapping(value = "/getEbookData", method = RequestMethod.POST)
    public ResponseEntity<Ebook> editEbook(@RequestBody Long ebookId) {
        System.out.println("Editing Ebook id" + ebookId);  
        Ebook ebook = ebookService.findById(ebookId); 
        return new ResponseEntity<Ebook>(ebook, HttpStatus.OK);
    }
	
	
	//-------------------Edit a Ebook--------------------------------------------------------
	
	@RequestMapping(value = "/ebookEdit", method = RequestMethod.POST)
	public ResponseEntity<Ebook> editEbook(@RequestPart("ebook") EbookDto ebookDto, @RequestPart(name="file",required=false) MultipartFile file) {
	    System.out.println("Creating Ebook " + ebookDto.getTitle());
	    //System.out.println("Creating Ebook " + file.getOriginalFilename());
	    System.out.println("Creating Ebook edited filename" + ebookDto.getFileName());
	    System.out.println("Creating Ebook " +   ebookDto);
	    
	    if(file==null){
			Ebook ebookToUpdate = ebookService.findById(ebookDto.getEbookId());
			Ebook ebook = ebookService.updateEbookWithoutFile(ebookToUpdate, ebookDto);
			System.out.println(ebook.getAuthor() + " and " + ebook.getTitle());
			return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
		}else{
			ebookService.deleteById(ebookDto.getEbookId());
			Ebook ebook = ebookService.updateEbookWithFile(ebookDto, file);
			return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
			
		}               
	}
       
    //------------------- Delete a Ebook --------------------------------------------------------
    
    @RequestMapping(value = "/deleteEbook", method = RequestMethod.DELETE)
    public ResponseEntity<Ebook> deleteEbook(@RequestBody Long ebookId) throws NotFoundException {
        System.out.println("Deleting Ebook with id " + ebookId);        
        ebookService.deleteById(ebookId);
        return new ResponseEntity<Ebook>(HttpStatus.OK);
    }
    
    //-------------------Add a Ebook--------------------------------------------------------
    
	@RequestMapping(value = "/ebookAdd", method = RequestMethod.POST)
	public ResponseEntity<Ebook> addEbook(@RequestPart("ebook") EbookAddDto ebookAddDto, @RequestPart("file") MultipartFile file) {
	    System.out.println("Creating Ebook " + ebookAddDto.getTitle());
	    System.out.println("Creating Ebook " + file.getOriginalFilename());
	    System.out.println("Creating Ebook edited filename" + ebookAddDto.getFileName());
	    System.out.println("Creating Ebook " +   ebookAddDto);
	    
	    Ebook ebook = ebookService.addEbook(ebookAddDto, file);
	    if (ebook == null) {
	    	//If file name already exists
	    	return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }	                 
	    return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
	}
	
	//-------------------Upload a Ebook--------------------------------------------------------
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Ebook> upload(@RequestBody MultipartFile file) {    
	    Ebook ebook = ebookService.upload(file);      
	    return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
	}
	
	

}
