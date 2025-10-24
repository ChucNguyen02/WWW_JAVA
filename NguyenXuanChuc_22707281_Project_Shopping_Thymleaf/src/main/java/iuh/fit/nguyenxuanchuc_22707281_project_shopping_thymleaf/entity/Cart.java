package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho giỏ hàng của người dùng.
 * Người dùng đăng nhập sẽ có Cart lưu trong DB.
 */
@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Mỗi Cart thuộc về một User.
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * Danh sách các mặt hàng trong giỏ.
     */
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CartItem> items = new ArrayList<>();

    /**
     * Thêm sản phẩm vào giỏ hàng.
     */
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng.
     */
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

    /**
     * Xóa tất cả sản phẩm trong giỏ.
     */
    public void clearCart() {
        for (CartItem item : items) {
            item.setCart(null);
        }
        items.clear();
    }
}