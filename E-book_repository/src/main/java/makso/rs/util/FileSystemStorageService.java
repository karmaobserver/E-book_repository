package makso.rs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import makso.rs.dto.EbookViewModel;
import makso.rs.model.Ebook;
import makso.rs.udd.indexer.IndexManager;


@Service
public class FileSystemStorageService implements StorageService  {
	
	private final Path rootLocation;
	
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    
    @Override
    public void store(MultipartFile file, Ebook ebook) {
		// TODO Auto-generated method stub
	    try {
	    	if (file.isEmpty()) {
	            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
	        }
	        
	        String filename = addTimestampToFilename(file.getOriginalFilename());
	        ebook.setFileName(filename);
	        
	        
	        if(Files.notExists(this.rootLocation.resolve(filename))){
	        	//Files.createDirectory(this.rootLocation.resolve(file.getOriginalFilename()));
	        	File temp = new File(ResourceBundle.getBundle("index").getString("docs"));
	        	boolean succ = temp.mkdirs();
	        }
	        
	        Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
	        File uploadedFile = new File(this.rootLocation.resolve(filename).toString());
	        IndexManager.getIndexer().index(uploadedFile,ebook);
	    } catch (IOException e) {
	        throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
	    }
	}
    
	private String addTimestampToFilename(String filename){
			
		String[] parts= filename.split("\\.");
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		return parts[0] + "_" + sdf.format(new Date()) + "." + parts[1] ;
		
		
	}
    
}
