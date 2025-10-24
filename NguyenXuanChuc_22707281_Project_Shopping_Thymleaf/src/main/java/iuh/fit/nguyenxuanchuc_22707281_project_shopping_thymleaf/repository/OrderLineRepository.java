package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.OrderLine;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.OrderLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho việc truy cập dữ liệu của Entity OrderLine.
 * JpaRepository<OrderLine, OrderLineId> có nghĩa là nó quản lý Entity 'OrderLine'
 * với khóa chính có kiểu dữ liệu là lớp phức hợp 'OrderLineId'.
 */
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLineId> {
}