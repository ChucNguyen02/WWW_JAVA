package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Customer;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Spring Data JPA tự động tạo query từ tên method
    List<Order> findByCustomer(Customer customer);
}