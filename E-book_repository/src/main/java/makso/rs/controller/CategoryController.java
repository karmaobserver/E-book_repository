package makso.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import makso.rs.dto.CategoryDto;
import makso.rs.dto.UserDto;
import makso.rs.model.Category;
import makso.rs.model.User;
import makso.rs.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = (List<Category>) categoryService.getAll();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	//------------------- Update a Category --------------------------------------------------------
    
    @RequestMapping(value = "/editCategory", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        System.out.println("Updating Category " + categoryDto.getName());
              
        Category currentCategory = categoryService.findById(categoryDto.getCategoryId());
          
        if (currentCategory==null) {
            System.out.println("Category with Id " + categoryDto.getCategoryId() + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
  
        currentCategory.setName(categoryDto.getName());
        
        categoryService.save(currentCategory);
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }

}

