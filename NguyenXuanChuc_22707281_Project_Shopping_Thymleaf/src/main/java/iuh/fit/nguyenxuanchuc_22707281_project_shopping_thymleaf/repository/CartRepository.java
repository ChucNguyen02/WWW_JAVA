package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Cart;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);
}