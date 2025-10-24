package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Category;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}