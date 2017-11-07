package makso.rs.controller;

import java.util.List;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import makso.rs.udd.searcher.InformationRetriever;
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
	
	@RequestMapping(value = "/ebooks", method = RequestMethod.GET)
	public ResponseEntity<List<Ebook>> getEbooks() {
		List<Ebook> ebooks = (List<Ebook>) ebookService.getAll();
		//List<Ebook> ebooks = InformationRetriever.getData(new MatchAllDocsQuery());
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
	
	//-------------------Edit a Ebook--------------------------------------------------------
    
    @RequestMapping(value = "/ebookEdit", method = RequestMethod.PUT)
    public ResponseEntity<Ebook> editEbook(@RequestBody EbookDto ebookDto) {
        System.out.println("Editing Ebook " + ebookDto.getTitle());
        System.out.println("Kategorija ID je:" + ebookDto.getCategoryId());
               
        if (ebookDto.getTitle() == null || ebookDto.getTitle().equals("") || ebookDto.getFileName() == null || ebookDto.getFileName().equals("") ) {
        	 System.out.println("A fields can't be null!");
        	 return new ResponseEntity<Ebook>(HttpStatus.NOT_ACCEPTABLE);
        }
        
        User ebookUser = userService.findById(ebookDto.getUserId());                        
        Category ebookCategory = categoryService.findById(ebookDto.getCategoryId());
        Language ebookLanguage = languageService.findById(ebookDto.getLanguageId());
        Ebook ebook = ebookService.findById(ebookDto.getEbookId());
      
        ebook.setTitle(ebookDto.getTitle());
        ebook.setAuthor(ebookDto.getAuthor());
        ebook.setKeywords(ebookDto.getKeywords());
        ebook.setPublicationYear(ebookDto.getPublicationYear());
        ebook.setFileName(ebookDto.getFileName());
        ebook.setCategory(ebookCategory);
        ebook.setLanguage(ebookLanguage);
        ebook.setUsers(ebookUser);
        
        ebookService.save(ebook);
  
        return new ResponseEntity<Ebook>(ebook, HttpStatus.OK);
    }
    
    
    //-------------------Create a Ebook--------------------------------------------------------
    
    /*@RequestMapping(value = "/ebookAdd", method = RequestMethod.POST)
    public ResponseEntity<Ebook> createEbook(@RequestBody EbookAddDto ebookAddDto) {
        System.out.println("Creating Ebook " + ebookAddDto.getTitle());
        
        //check if filename already exists
        if (ebookService.findEbookByFileName(ebookAddDto.getFileName()) != null) {
            System.out.println("A Filename with name " + ebookAddDto.getFileName() + " already exist");
            return new ResponseEntity<Ebook>(HttpStatus.CONFLICT);
        }
        
        Ebook ebook = new Ebook();
        
        User ebookUser = userService.findById(ebookAddDto.getUserId());                        
        Category ebookCategory = categoryService.findById(ebookAddDto.getCategoryId());
        Language ebookLanguage = languageService.findById(ebookAddDto.getLanguageId());
        
        ebook.setTitle(ebookAddDto.getTitle());
        ebook.setAuthor(ebookAddDto.getAuthor());
        ebook.setKeywords(ebookAddDto.getKeywords());
        ebook.setPublicationYear(ebookAddDto.getPublicationYear());
        ebook.setFileName(ebookAddDto.getFileName());
        ebook.setCategory(ebookCategory);
        ebook.setLanguage(ebookLanguage);
        ebook.setUsers(ebookUser);
        ebook.setMime(ebookAddDto.getMime());
                     
        ebookService.save(ebook);
        return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
    }*/
    
    
    //------------------- Delete a Ebook --------------------------------------------------------
    
    @RequestMapping(value = "/deleteEbook", method = RequestMethod.DELETE)
    public ResponseEntity<Ebook> deleteEbook(@RequestBody long ebookId) {
        System.out.println("Deleting Ebook with id " + ebookId);
  
        Ebook ebook = ebookService.findById(ebookId);
        if (ebook == null) {
            System.out.println("Unable to delete. Ebook with id " + ebookId + " not found");
            return new ResponseEntity<Ebook>(HttpStatus.NOT_FOUND);
        }
  
        ebookService.deleteById(ebookId);
        return new ResponseEntity<Ebook>(HttpStatus.NO_CONTENT);
    }
    
    //-------------------Add a Ebook--------------------------------------------------------
    
	@RequestMapping(value = "/ebookAdd", method = RequestMethod.POST)
	public ResponseEntity<Ebook> addEbook(@RequestBody EbookViewModel ebookViewModel) {
	    System.out.println("Creating Ebook " + ebookViewModel.getEbook().getTitle());
	    System.out.println("Creating Ebook " + ebookViewModel.getFile().getName());
	    
	   /* //check if filename already exists
	    if (ebookService.findEbookByFileName(ebookAddDto.getFileName()) != null) {
	        System.out.println("A Filename with name " + ebookAddDto.getFileName() + " already exist");
	        return new ResponseEntity<Ebook>(HttpStatus.CONFLICT);
	    }*/
	    
	    ebookViewModel.getEbook().setId(System.currentTimeMillis());
		storageService.store(ebookViewModel.getFile(),ebookViewModel.getEbook());
	                 
	    return new ResponseEntity<Ebook>(ebookViewModel.getEbook(), HttpStatus.CREATED);
	}
	
	//-------------------Add a Ebook--------------------------------------------------------
    
	/*@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Ebook> upload(@RequestBody MultipartFile file) {
	    System.out.println("Adding Ebook file name: " + file);
	   
	    
	    Ebook ebook = EbookPDFHandler.createEbookFromPDF(file);
		System.out.println(ebook);
	                 
	    return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
	}*/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Ebook> upload(@RequestBody MultipartFile file) {
	    System.out.println("Adding Ebook file name: " + file);
	   
	    
	    Ebook ebook = EbookPDFHandler.createEbookFromPDF(file);
		System.out.println(ebook);
	                 
	    return new ResponseEntity<Ebook>(ebook, HttpStatus.CREATED);
	}

}
