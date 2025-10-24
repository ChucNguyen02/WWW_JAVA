package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho việc truy cập dữ liệu của Entity Category.
 * JpaRepository<Category, Integer> có nghĩa là nó quản lý Entity 'Category'
 * với khóa chính có kiểu dữ liệu là 'Integer'.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}