package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.controller.cart;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.*;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.CartService;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    /**
     * Xem giỏ hàng
     */
    @GetMapping
    public String viewCart(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            // User đã đăng nhập
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            Cart cart = cartService.getCartByUser(user);
            BigDecimal total = cartService.calculateCartTotal(user);
            int itemCount = cartService.getCartItemCount(user);

            model.addAttribute("cartItems", cart.getItems());
            model.addAttribute("cartTotal", total);
            model.addAttribute("itemCount", itemCount);
            model.addAttribute("isAuthenticated", true);
        } else {
            // Guest user
            List<CartItem> guestItems = cartService.getGuestCartItems(session);
            BigDecimal total = cartService.calculateGuestCartTotal(session);
            int itemCount = cartService.getGuestCartItemCount(session);

            model.addAttribute("cartItems", guestItems);
            model.addAttribute("cartTotal", total);
            model.addAttribute("itemCount", itemCount);
            model.addAttribute("isAuthenticated", false);
        }

        return "cart/view";
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                            Principal principal,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            if (principal != null) {
                User user = userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
                cartService.addToCart(user, productId, quantity);
            } else {
                cartService.addToGuestCart(session, productId, quantity);
            }

            redirectAttributes.addFlashAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ
     */
    @PostMapping("/update")
    public String updateCartItem(@RequestParam(value = "cartItemId", required = false) Integer cartItemId,
                                 @RequestParam("productId") Integer productId,
                                 @RequestParam("quantity") Integer quantity,
                                 Principal principal,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (principal != null) {
                User user = userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
                cartService.updateCartItemQuantity(user, cartItemId, quantity);
            } else {
                cartService.updateGuestCartItemQuantity(session, productId, quantity);
            }

            redirectAttributes.addFlashAttribute("message", "Đã cập nhật giỏ hàng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @GetMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Integer productId,
                                 Principal principal,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (principal != null) {
                User user = userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
                cartService.removeFromCart(user, productId);
            } else {
                cartService.removeFromGuestCart(session, productId);
            }
            redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    @GetMapping("/clear")
    public String clearCart(Principal principal,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            if (principal != null) {
                User user = userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
                cartService.clearCart(user);
            } else {
                cartService.clearGuestCart(session);
            }

            redirectAttributes.addFlashAttribute("message", "Đã xóa toàn bộ giỏ hàng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }
}
