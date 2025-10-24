package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity đại diện cho một dòng trong chi tiết đơn hàng,
 * chứa thông tin về một sản phẩm cụ thể trong một đơn hàng cụ thể.
 */
@Entity
@Table(name = "order_line")
@Getter
@Setter
@NoArgsConstructor
public class OrderLine {

    /**
     * Sử dụng khóa chính phức hợp đã được định nghĩa trong lớp OrderLineId.
     */
    @EmbeddedId
    private OrderLineId id;

    /**
     * Mối quan hệ Nhiều-Một tới Order.
     * @MapsId("orderId") ánh xạ thuộc tính 'orderId' trong lớp OrderLineId
     * với mối quan hệ này. Nó chỉ định rằng phần 'orderId' của khóa chính
     * cũng là khóa ngoại tới entity Order.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Mối quan hệ Nhiều-Một tới Product.
     * @MapsId("productId") ánh xạ thuộc tính 'productId' trong lớp OrderLineId
     * với mối quan hệ này.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Số lượng sản phẩm trong dòng đơn hàng này.
     */
    @Column(name = "amount", nullable = false)
    private Integer amount;

    /**
     * Giá của sản phẩm tại thời điểm mua hàng.
     * Được lưu lại để tránh việc giá sản phẩm thay đổi trong tương lai
     * ảnh hưởng đến lịch sử đơn hàng.
     */
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;
}