package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * Lớp này đại diện cho khóa chính phức hợp của entity OrderLine.
 * Một khóa chính phức hợp trong JPA phải implement Serializable.
 * @Embeddable chỉ ra rằng lớp này có thể được nhúng vào một entity khác.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // Bắt buộc phải có equals() và hashCode() cho các lớp khóa chính
public class OrderLineId implements Serializable {

    /**
     * ID của đơn hàng (sẽ là khóa ngoại tới bảng 'order').
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * ID của sản phẩm (sẽ là khóa ngoại tới bảng 'product').
     */
    @Column(name = "product_id")
    private Integer productId;
}