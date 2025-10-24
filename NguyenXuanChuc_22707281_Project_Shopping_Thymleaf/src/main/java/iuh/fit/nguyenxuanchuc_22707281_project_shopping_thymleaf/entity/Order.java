package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private Calendar orderDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

    public BigDecimal getTotal() {
        if (totalAmount != null) {
            return totalAmount;
        }
        return orderLines.stream()
                .map(line -> line.getPurchasePrice().multiply(BigDecimal.valueOf(line.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateTotal() {
        this.totalAmount = getTotal();
    }

    @PrePersist
    public void prePersist() {
        if (orderDate == null) {
            orderDate = Calendar.getInstance();
        }
        calculateTotal();
    }

    @PreUpdate
    public void preUpdate() {
        calculateTotal();
    }

    public enum OrderStatus {
        PENDING("Chờ xử lý"),
        PROCESSING("Đang xử lý"),
        SHIPPED("Đã gửi hàng"),
        DELIVERED("Đã giao hàng"),
        CANCELLED("Đã hủy");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}