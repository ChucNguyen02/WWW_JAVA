package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Cart;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.CartItem;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}