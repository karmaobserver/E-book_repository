package makso.rs.service;

import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;
import makso.rs.dto.CategoryDto;
import makso.rs.model.Category;
import makso.rs.model.Ebook;
import makso.rs.model.User;
import makso.rs.repository.CategoryRepository;
import makso.rs.repository.UserRepository;
import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.searcher.ResultRetriever;
import makso.rs.util.StorageService;
@Service
@Transactional
public class CategoryService implements GenericService<Category>{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userService;
	
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category t) {
		return categoryRepository.save(t);
	}

	@Override
	public Category findById(long id) {
		return categoryRepository.findOne(id);
	}
	
	public Category findCategoryByName(String name) {
		Category category = new Category();
		category = categoryRepository.findCategoryByName(name);
		return category;
	}

	@Override
	public void deleteById(long id) {
		categoryRepository.delete(id);
	}
	
	public ResponseEntity<Category> updateCategory(CategoryDto categoryDto) {  
		Category currentCategory = findById(categoryDto.getCategoryId());      
        if (currentCategory==null) {
            System.out.println("Category with Id " + categoryDto.getCategoryId() + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }      
        
        if (findCategoryByName(categoryDto.getName()) != null) {
            System.out.println("A Category with name " + categoryDto.getName() + " already exist");
            return new ResponseEntity<Category>(HttpStatus.CONFLICT);
        }   
        
        if (categoryDto.getName() == null || categoryDto.getName().equals("")) {
        	 System.out.println("A Category can't be null!");
        	 return new ResponseEntity<Category>(HttpStatus.NOT_ACCEPTABLE);
        }
         
        //Index category in documents
        String oldNameCategory = currentCategory.getName();       
        QueryParser qp = new QueryParser(Version.LUCENE_4_9,"category",new WhitespaceAnalyzer(Version.LUCENE_4_9));
		List<Document> doc = null;
		try {
			doc = ResultRetriever.getResults(qp.parse("category:" + oldNameCategory));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		IndexableField field = new StringField("category", categoryDto.getName(), Store.YES);
		doc.stream().forEach(d -> IndexManager.getIndexer().updateDocument(d, field));
				
        currentCategory.setName(categoryDto.getName());      
        save(currentCategory);		
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
	}
	
	public ResponseEntity<Category> addCategory(String categoryName) {
		if (findCategoryByName(categoryName) != null) {
            System.out.println("A Category with name " + categoryName + " already exist");
            return new ResponseEntity<Category>(HttpStatus.CONFLICT);
        }   
		Category category = new Category();      
        category.setName(categoryName); 
        save(category);  
        return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	
	public ResponseEntity<Category> deleteCategory(long categoryId) {
		System.out.println("Deleting Category with id " + categoryId);		  
        Category category = findById(categoryId);
        if (category == null) {
            System.out.println("Unable to delete. Category with id " + categoryId + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        
        //deleting all ebooks which belong to that category
        Query q = new TermQuery(new Term("category", category.getName()));
		List<Document> doc = ResultRetriever.getResults(q);
		IndexManager.getIndexer().deleteDocuments(q);	
		if(!doc.isEmpty()){
			for (Document document : doc) {
				storageService.deleteFile(document);
			}
		}
			
		//deleting all users which belong to that category
		List<User> usersWithCategory = userRepository.findUsersByCategoryId(categoryId);
		for (User user : usersWithCategory) {
			userService.deleteUser(user.getId());
			//userRepository.delete(user.getId());
		}
		
        deleteById(categoryId);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
	}
}
