package makso.rs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.lucene.document.Document;
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
	public void init() {
    	try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }	
	}
    
    @Override
    public void store(MultipartFile file, Ebook ebook) {
		// TODO Auto-generated method stub
	    try {
	    	if (file.isEmpty()) {
	            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
	        }
	        	        
	        if(Files.notExists(this.rootLocation.resolve(ebook.getFileName()))){
	        	//Files.createDirectory(this.rootLocation.resolve(file.getOriginalFilename()));
	        	File temp = new File(ResourceBundle.getBundle("index").getString("docs"));
	        	boolean dir = temp.mkdirs();
	        	System.out.println("created dir: " + dir);
	        }
	        
	        Files.copy(file.getInputStream(), this.rootLocation.resolve(ebook.getFileName()));
	        File uploadedFile = new File(this.rootLocation.resolve(ebook.getFileName()).toString());
	        IndexManager.getIndexer().index(uploadedFile, ebook);
	    } catch (IOException e) {
	        throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
	    } 
	}
    
	@Override
	public void deleteFile(Document doc) {
		File file = new File(ResourceBundle.getBundle("index").getString("docs") + "\\" + doc.getField("fileName").stringValue() );
		try {
			boolean result = Files.deleteIfExists(file.toPath());
			System.out.println("File is deleted: " + result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
}
