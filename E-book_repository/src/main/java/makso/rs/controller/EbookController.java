package makso.rs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import javassist.NotFoundException;
import makso.rs.dto.EbookAddDto;
import makso.rs.dto.EbookDto;
import makso.rs.model.Ebook;
import makso.rs.service.EbookService;

@RestController
@RequestMapping("/api")
public class EbookController {
	
	@Autowired
	EbookService ebookService;
			
	private static final int BUFFER_SIZE = 4*1024;
	
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
	    if(file==null){
			Ebook ebookToUpdate = ebookService.findById(ebookDto.getEbookId());
			Ebook ebook = ebookService.updateEbookWithoutFile(ebookToUpdate, ebookDto);
			if (ebook == null) {
		    	//If file name already exists
		    	return new ResponseEntity<>(HttpStatus.CONFLICT);
		    }
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
	
	//-------------------Download a Ebook--------------------------------------------------------
	
	@RequestMapping(value = "/downloadEbook/{fileName}", method = RequestMethod.GET)
	public void downloadEbook(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		System.out.println("Download filename: " + fileName);	
		if(!fileName.endsWith(".pdf")){
			fileName+=".pdf";
        }
		
		String filePath = ResourceBundle.getBundle("index").getString("docs") + File.separator + fileName;
		File downloadFile = new File(filePath);
		System.out.println(downloadFile.getName());
        FileInputStream inputStream = new FileInputStream(downloadFile);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");    
		response.addHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
		response.setContentLength((int) downloadFile.length());
		       
        OutputStream outStream = response.getOutputStream();
        
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();	
	}	
}
