package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.*;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private static final String SESSION_CART_KEY = "GUEST_CART";

    /**
     * Lấy giỏ hàng của User đã đăng nhập
     */
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    /**
     * Lấy giỏ hàng của Guest từ Session
     */
    @SuppressWarnings("unchecked")
    public Map<Integer, Integer> getGuestCart(HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng User
     */
    public void addToCart(User user, Integer productId, Integer quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;

            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
            }

            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity);
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
        }
    }

    /**
     * Thêm sản phẩm vào giỏ hàng Guest
     */
    public void addToGuestCart(HttpSession session, Integer productId, Integer quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
        }

        Map<Integer, Integer> cart = getGuestCart(session);
        int currentQty = cart.getOrDefault(productId, 0);
        int newQty = currentQty + quantity;

        if (product.getStockQuantity() < newQty) {
            throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
        }

        cart.put(productId, newQty);
        session.setAttribute(SESSION_CART_KEY, cart);
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng User
     */
    public void updateCartItemQuantity(User user, Integer cartItemId, Integer newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        // So sánh id thay vì equals để tránh so sánh tham chiếu
        if (item.getCart() == null || item.getCart().getUser() == null || item.getCart().getUser().getId() == null
                || !item.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền thao tác giỏ hàng này");
        }

        if (newQuantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            if (item.getProduct().getStockQuantity() < newQuantity) {
                throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + item.getProduct().getStockQuantity());
            }
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        }
    }

    /**
     * Cập nhật số lượng trong giỏ hàng Guest
     */
    public void updateGuestCartItemQuantity(HttpSession session, Integer productId, Integer newQuantity) {
        Map<Integer, Integer> cart = getGuestCart(session);

        if (newQuantity <= 0) {
            cart.remove(productId);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Không đủ hàng trong kho. Còn lại: " + product.getStockQuantity());
            }
            cart.put(productId, newQuantity);
        }

        session.setAttribute(SESSION_CART_KEY, cart);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng User
     */
    public void removeFromCart(User user, Integer cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (item.getCart() == null || item.getCart().getUser() == null || item.getCart().getUser().getId() == null
                || !item.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền thao tác giỏ hàng này");
        }

        cartItemRepository.delete(item);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng Guest
     */
    public void removeFromGuestCart(HttpSession session, Integer productId) {
        Map<Integer, Integer> cart = getGuestCart(session);
        cart.remove(productId);
        session.setAttribute(SESSION_CART_KEY, cart);
    }

    /**
     * Lấy danh sách CartItem từ giỏ hàng Guest
     */
    public List<CartItem> getGuestCartItems(HttpSession session) {
        Map<Integer, Integer> guestCart = getGuestCart(session);
        List<CartItem> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : guestCart.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElse(null);
            if (product != null) {
                CartItem item = new CartItem();
                item.setProduct(product);
                item.setQuantity(entry.getValue());
                items.add(item);
            }
        }

        return items;
    }

    /**
     * Tính tổng tiền giỏ hàng User
     */
    public BigDecimal calculateCartTotal(User user) {
        Cart cart = getCartByUser(user);
        return cart.getItems().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Tính tổng tiền giỏ hàng Guest
     */
    public BigDecimal calculateGuestCartTotal(HttpSession session) {
        List<CartItem> items = getGuestCartItems(session);
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Xóa toàn bộ giỏ hàng User
     */
    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.clearCart();
        cartRepository.save(cart);
    }

    /**
     * Xóa toàn bộ giỏ hàng Guest
     */
    public void clearGuestCart(HttpSession session) {
        session.removeAttribute(SESSION_CART_KEY);
    }

    /**
     * Merge giỏ hàng Guest vào User khi đăng nhập
     */
    public void mergeGuestCartToUser(HttpSession session, User user) {
        Map<Integer, Integer> guestCart = getGuestCart(session);

        if (guestCart.isEmpty()) {
            return;
        }

        for (Map.Entry<Integer, Integer> entry : guestCart.entrySet()) {
            try {
                addToCart(user, entry.getKey(), entry.getValue());
            } catch (RuntimeException e) {
                // Log lỗi nhưng vẫn tiếp tục merge các sản phẩm khác
                System.err.println("Error merging product " + entry.getKey() + ": " + e.getMessage());
            }
        }

        clearGuestCart(session);
    }

    /**
     * Đếm số lượng items trong giỏ hàng User
     */
    public int getCartItemCount(User user) {
        Cart cart = getCartByUser(user);
        return cart.getItems().size();
    }

    /**
     * Đếm số lượng items trong giỏ hàng Guest
     */
    public int getGuestCartItemCount(HttpSession session) {
        Map<Integer, Integer> cart = getGuestCart(session);
        return cart.size();
    }

    /**
     * Kiểm tra xem giỏ hàng có rỗng không
     */
    public boolean isCartEmpty(User user) {
        Cart cart = getCartByUser(user);
        return cart.getItems().isEmpty();
    }

    /**
     * Kiểm tra xem giỏ hàng Guest có rỗng không
     */
    public boolean isGuestCartEmpty(HttpSession session) {
        Map<Integer, Integer> cart = getGuestCart(session);
        return cart.isEmpty();
    }
}