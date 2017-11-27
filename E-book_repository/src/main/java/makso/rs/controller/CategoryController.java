package makso.rs.controller;

import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.dto.CategoryDto;
import makso.rs.model.Category;
import makso.rs.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	private static Log4JLogger logger = new Log4JLogger(CategoryController.class.getName());
	
	//-------------------List all categories---------------------------------------------------------
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = (List<Category>) categoryService.getAll();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	//------------------- Update a Category -----------------------------------------------------
    	
	@RequestMapping(value = "/editCategory", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto) {            
        ResponseEntity<Category> responseEntity = categoryService.updateCategory(categoryDto);             
        return responseEntity;
    }
	 
    //-------------------Add a Category--------------------------------------------------------
    
    @RequestMapping(value = "/categoryAdd", method = RequestMethod.POST)
    public ResponseEntity<Category> addCategory(@RequestBody String categoryName) {
    	ResponseEntity<Category> responseEntity = categoryService.addCategory(categoryName);     
        return responseEntity;
    }
    
    //------------------- Delete a Category --------------------------------------------------------
    
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@RequestBody long categoryId) {
    	ResponseEntity<Category> responseEntity = categoryService.deleteCategory(categoryId);
        return responseEntity;
    }

}

