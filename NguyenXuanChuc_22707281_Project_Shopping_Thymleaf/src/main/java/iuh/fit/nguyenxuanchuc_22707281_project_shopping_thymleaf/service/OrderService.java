package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.*;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    /**
     * Tìm đơn hàng theo username
     */
    public List<Order> findOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));

        Customer customer = user.getCustomer();
        if (customer == null) {
            return Collections.emptyList();
        }

        return orderRepository.findByCustomer(customer);
    }

    /**
     * Tạo đơn hàng từ giỏ hàng
     */
    public Order createOrderFromCart(String username, String shippingAddress, String paymentMethod) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new RuntimeException("Người dùng chưa có hồ sơ khách hàng");
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể tạo đơn hàng");
        }

        // Kiểm tra tồn kho trước khi tạo đơn
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + product.getName() +
                        "' không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
            }
        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(Calendar.getInstance());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress != null ? shippingAddress : customer.getAddress());
        order.setPaymentMethod(paymentMethod != null ? paymentMethod : "COD");

        Set<OrderLine> orderLines = new HashSet<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // Giảm tồn kho
            product.reduceStock(cartItem.getQuantity());
            productRepository.save(product);

            // Tạo OrderLine
            OrderLine orderLine = new OrderLine();
            OrderLineId orderLineId = new OrderLineId();
            orderLineId.setProductId(product.getId());

            orderLine.setId(orderLineId);
            orderLine.setOrder(order);
            orderLine.setProduct(product);
            orderLine.setAmount(cartItem.getQuantity());
            orderLine.setPurchasePrice(product.getPrice());

            orderLines.add(orderLine);
        }

        order.setOrderLines(orderLines);
        order.calculateTotal();

        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clearCart(user);

        return savedOrder;
    }

    /**
     * Lấy danh sách OrderLine của đơn hàng
     */
    public List<OrderLine> getOrderLines(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        return new ArrayList<>(order.getOrderLines());
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    public void updateOrderStatus(Integer orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Kiểm tra logic chuyển trạng thái
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Không thể thay đổi trạng thái của đơn hàng đã hủy");
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED && status != Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Không thể thay đổi trạng thái của đơn hàng đã giao");
        }

        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * Hủy đơn hàng
     */
    public void cancelOrder(Integer orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Kiểm tra quyền hủy đơn
        if (!order.getCustomer().getUser().equals(user)) {
            throw new RuntimeException("Bạn không có quyền hủy đơn hàng này");
        }

        // Chỉ cho phép hủy đơn ở trạng thái PENDING hoặc PROCESSING
        if (order.getStatus() != Order.OrderStatus.PENDING &&
                order.getStatus() != Order.OrderStatus.PROCESSING) {
            throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái: " + order.getStatus().getDisplayName());
        }

        // Hoàn trả tồn kho
        for (OrderLine orderLine : order.getOrderLines()) {
            Product product = orderLine.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderLine.getAmount());
            productRepository.save(product);
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    /**
     * Tìm đơn hàng theo Customer
     */
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    /**
     * Thống kê đơn hàng theo trạng thái
     */
    public Map<Order.OrderStatus, Long> countOrdersByStatus() {
        List<Order> allOrders = orderRepository.findAll();
        Map<Order.OrderStatus, Long> statistics = new EnumMap<>(Order.OrderStatus.class);

        for (Order.OrderStatus status : Order.OrderStatus.values()) {
            long count = allOrders.stream()
                    .filter(order -> order.getStatus() == status)
                    .count();
            statistics.put(status, count);
        }

        return statistics;
    }
}