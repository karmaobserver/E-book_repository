package makso.rs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import makso.rs.model.Category;
import makso.rs.model.User;
import makso.rs.repository.CategoryRepository;
@Service
@Transactional
public class CategoryService implements GenericService<Category>{

	@Autowired
	private CategoryRepository categoryRepository;
	
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

}
