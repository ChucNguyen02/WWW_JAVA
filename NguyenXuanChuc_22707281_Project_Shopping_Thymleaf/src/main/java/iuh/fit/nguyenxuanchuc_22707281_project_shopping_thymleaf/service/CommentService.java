package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Comment;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Product;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.CommentRepository;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.ProductRepository;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Đăng đánh giá mới
     */
    public Comment postComment(Integer productId, String text, String username) {
        if (text == null || text.trim().isEmpty()) {
            throw new RuntimeException("Nội dung đánh giá không được để trống");
        }

        if (text.length() > 1000) {
            throw new RuntimeException("Nội dung đánh giá không được quá 1000 ký tự");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Comment comment = new Comment();
        comment.setProduct(product);
        comment.setAuthor(author);
        comment.setText(text.trim());
        comment.setCreatedAt(Calendar.getInstance());

        return commentRepository.save(comment);
    }

    /**
     * Xóa đánh giá (Admin hoặc chính user đã đăng)
     */
    public void deleteComment(Integer commentId, String username, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));

        // Kiểm tra quyền xóa
        if (!isAdmin && !comment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Bạn không có quyền xóa đánh giá này");
        }

        commentRepository.delete(comment);
    }

    /**
     * Xóa đánh giá (chỉ dành cho Admin)
     */
    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * Lấy tất cả đánh giá của sản phẩm
     */
    public List<Comment> getCommentsByProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        return product.getComments();
    }

    /**
     * Lấy tất cả đánh giá của user
     */
    public List<Comment> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return user.getComments();
    }

    /**
     * Sửa đánh giá
     */
    public void updateComment(Integer commentId, String newText, String username) {
        if (newText == null || newText.trim().isEmpty()) {
            throw new RuntimeException("Nội dung đánh giá không được để trống");
        }

        if (newText.length() > 1000) {
            throw new RuntimeException("Nội dung đánh giá không được quá 1000 ký tự");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));

        // Kiểm tra quyền sửa
        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Bạn không có quyền sửa đánh giá này");
        }

        comment.setText(newText.trim());
        commentRepository.save(comment);
    }
}