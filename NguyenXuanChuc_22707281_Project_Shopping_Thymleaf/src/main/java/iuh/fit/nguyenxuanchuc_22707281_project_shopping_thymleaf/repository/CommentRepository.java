package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho việc truy cập dữ liệu của Entity Comment.
 * JpaRepository<Comment, Integer> có nghĩa là nó quản lý Entity 'Comment'
 * với khóa chính có kiểu dữ liệu là 'Integer'.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}