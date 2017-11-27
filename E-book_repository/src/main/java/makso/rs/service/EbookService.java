package makso.rs.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.pdfbox.filter.DCTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import makso.rs.dto.EbookAddDto;
import makso.rs.dto.EbookDto;
import makso.rs.model.Category;
import makso.rs.model.Ebook;
import makso.rs.model.Language;
import makso.rs.model.User;
import makso.rs.repository.EbookRepository;
import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.searcher.InformationRetriever;
import makso.rs.udd.searcher.ResultRetriever;
import makso.rs.util.EbookPDFHandler;
import makso.rs.util.StorageService;

@Service
public class EbookService implements GenericService<Ebook>{

	@Autowired
	private EbookRepository ebookRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LanguageService languageService;
	
	public List<Ebook> getAll() {
		return InformationRetriever.getData(new MatchAllDocsQuery());
	}

	@Override
	public Ebook save(Ebook t) {
		return ebookRepository.save(t);
	}

	@Override
	public Ebook findById(long id) {
		Query q = new TermQuery(new Term("ebookId", Long.toString(id)));
		List<Ebook> ebooksList = InformationRetriever.getData(q);
		
		if(ebooksList.isEmpty()){
			try {
				throw new NotFoundException("Ebook requested not found.");
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ebooksList.get(0);
	}
	
	public List<Ebook> getEbooksByCategory(long categoryId) {
		Category category = categoryService.findById(categoryId);
		Query q = new TermQuery(new Term("category", category.getName()));
		List<Ebook> ebooksList = InformationRetriever.getData(q);
		return ebooksList;
	}
	
	public Ebook addEbook(EbookAddDto ebookAddDto, MultipartFile file) {
		
		Ebook ebook = new Ebook();
	        
		 //check if filename already exists
		System.out.println(ebookAddDto.getFileName());
	    Query q = new TermQuery(new Term("fileName", ebookAddDto.getFileName()+".pdf"));
		List<Document> doc = ResultRetriever.getResults(q);
	    if (!doc.isEmpty()) {
	        System.out.println("A Filename with name " + ebookAddDto.getFileName() + " already exist");
	        return null;
	    }
	  
        User ebookUser = userService.findById(ebookAddDto.getUserId());                        
        Category ebookCategory = categoryService.findById(ebookAddDto.getCategoryId());
        Language ebookLanguage = languageService.findById(ebookAddDto.getLanguageId());;
        
        ebook.setTitle(ebookAddDto.getTitle());
        ebook.setAuthor(ebookAddDto.getAuthor());
        ebook.setKeywords(ebookAddDto.getKeywords());
        ebook.setPublicationYear(ebookAddDto.getPublicationYear());
        ebook.setFileName(ebookAddDto.getFileName()+".pdf");
        ebook.setCategory(ebookCategory);
        ebook.setLanguage(ebookLanguage);
        ebook.setUsers(ebookUser);
        ebook.setMime(ebookAddDto.getMime());
	    System.out.println(ebookAddDto.getFileName()+".pdf");
	    System.out.println(ebook.getCategory().getName());
	    System.out.println(ebook.getLanguage().getName());
        
        //TODO make better way
        ebook.setId(System.currentTimeMillis());
        
        //Store ebook
		storageService.store(file, ebook);
		
		return ebook;
	}
	
	public Ebook updateEbookWithoutFile(Ebook oldEbook, EbookDto ebookDto) {
		
		String newFileName = ebookDto.getFileName()+".pdf";
		 //check if filename already exists
		if (!newFileName.equals(oldEbook.getFileName())) {
		    Query qq = new TermQuery(new Term("fileName", ebookDto.getFileName()+".pdf"));
			List<Document> docc = ResultRetriever.getResults(qq);
		    if (!docc.isEmpty()) {
		        System.out.println("A Filename with name " + ebookDto.getFileName() + " already exist");
		        return null;
		    }
		}
		
		Query q = new TermQuery(new Term("ebookId", oldEbook.getId().toString()));
		Document doc = ResultRetriever.getResults(q).get(0);
		
		System.out.println("Dokucment: " + doc);
		
		Ebook newEbook = new Ebook();
		
		User ebookUser = userService.findById(ebookDto.getUserId());                        
	    Category ebookCategory = categoryService.findById(ebookDto.getCategoryId());
	    Language ebookLanguage = languageService.findById(ebookDto.getLanguageId());;
	       
	    newEbook.setTitle(ebookDto.getTitle());
	    newEbook.setAuthor(ebookDto.getAuthor());
	    newEbook.setKeywords(ebookDto.getKeywords());
	    newEbook.setPublicationYear(ebookDto.getPublicationYear());
	    newEbook.setFileName(ebookDto.getFileName()+".pdf");
	    newEbook.setCategory(ebookCategory);
        newEbook.setLanguage(ebookLanguage);
        newEbook.setUsers(ebookUser);
        newEbook.setMime(ebookDto.getMime());
	    System.out.println(newEbook.getFileName()+".pdf");
	    System.out.println(newEbook.getCategory().getName());
	    System.out.println(newEbook.getLanguage().getName());
	    newEbook.setId(ebookDto.getEbookId());
        
        List<IndexableField> fields = generateFields(newEbook);
		IndexManager.getIndexer().updateDocument(doc,fields.stream().toArray(IndexableField[]::new));

        return newEbook;
	}
	
	public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
	
	private static List<IndexableField> generateFields(Ebook newEbook) {
		
		final Ebook ebook = newEbook;	
		List<IndexableField> fields = new ArrayList<>();
		List<Field> fieldsOfClass = getAllFields(Ebook.class);	
		
		System.out.println(fieldsOfClass.size());
		for (int i=0; i<fieldsOfClass.size()-1; i++) {
			fields.addAll(generateField(ebook, fieldsOfClass.get(i)));
		}
		return fields;
	}

	private static List<IndexableField> generateField(Ebook newEbook, Field field) {
		
		System.out.println("GEN");
		
		String name = field.getName();
		String value = get(newEbook, name).toString();
		
		System.out.println("Name Filed: " + name);
		
		List<IndexableField> fields = new ArrayList<>();
		if(name.equals("keywords")){
			String[] kws = value.trim().split(" ");
			for(String kw : kws){
				if(!kw.trim().equals("")){
					fields.add(new TextField("keyword", kw, Store.YES));
				}
			}
		}
		else if(name.equals("author") || name.equals("title")){
			fields.add( new TextField(name , value , Store.YES));
		}else{
			fields.add( new StringField(name,value,Store.YES));
		}
		return fields;
	}

	
	
	private static Object get(Object object, String fieldName) {
		
	    Class<?> clazz = object.getClass();
	    Object value = null;
	    while (clazz != null) {
	        try {
	        	System.out.println("OBJ");
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            
	            if(field.getName().equals("category")){
	            	Category c = (Category) field.get(object);
	            	value = c.getName();
	            }else if(field.getName().equals("language")){
	            	Language l = (Language) field.get(object);
	            	value = l.getName();
	            }else{
	            	value = field.get(object);
	            }
	            
	            
	            break;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return value;
	}
	
	public Ebook updateEbookWithFile(EbookDto ebookDto, MultipartFile file) {
		
		Ebook ebook = new Ebook();
		
		System.out.println("IME FAJLA:");
		System.out.println(ebookDto.getFileName());
        
		 //check if filename already exists
	    Query q = new TermQuery(new Term("fileName", ebookDto.getFileName()+".pdf"));
		List<Document> doc = ResultRetriever.getResults(q);
	    if (!doc.isEmpty()) {
	        System.out.println("A Filename with name " + ebookDto.getFileName() + " already exist");
	        return null;
	    }
	  
       User ebookUser = userService.findById(ebookDto.getUserId());                        
       Category ebookCategory = categoryService.findById(ebookDto.getCategoryId());
       Language ebookLanguage = languageService.findById(ebookDto.getLanguageId());;
       
       ebook.setTitle(ebookDto.getTitle());
       ebook.setAuthor(ebookDto.getAuthor());
       ebook.setKeywords(ebookDto.getKeywords());
       ebook.setPublicationYear(ebookDto.getPublicationYear());
       ebook.setFileName(ebookDto.getFileName()+".pdf");
       ebook.setCategory(ebookCategory);
       ebook.setLanguage(ebookLanguage);
       ebook.setUsers(ebookUser);
       ebook.setMime(ebookDto.getMime());
	   System.out.println(ebookDto.getFileName()+".pdf");
	   System.out.println(ebook.getCategory().getName());
	   System.out.println(ebook.getLanguage().getName());
       ebook.setId(ebookDto.getEbookId());
       
       //Store ebook
       storageService.store(file, ebook);
		
       return ebook;
	}
	
	
	public Ebook upload(MultipartFile file) {
		System.out.println("Adding Ebook file name: " + file.getOriginalFilename());    
	    Ebook ebook = EbookPDFHandler.createEbookFromPDF(file);
	    return ebook;
	}

	@Override
	public void deleteById(long ebookId) {		
		Query q = new TermQuery(new Term("ebookId", Long.toString(ebookId)));
		List<Document> doc = ResultRetriever.getResults(q);
		IndexManager.getIndexer().deleteDocuments(q);
		//IndexManager.getIndexer().deleleDocument(ebookId.intValue());
		
		if(doc.isEmpty()){
			try {
				throw new NotFoundException("Ebook requested for deletion not found.");
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		storageService.deleteFile(doc.get(0));
	}
}
