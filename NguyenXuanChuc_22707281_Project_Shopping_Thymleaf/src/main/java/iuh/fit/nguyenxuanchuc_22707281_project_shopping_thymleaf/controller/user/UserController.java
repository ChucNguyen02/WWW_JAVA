package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.controller.user;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Order;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.CartService;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.CommentService;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.OrderService;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    /**
     * Xem danh sách đơn hàng của tôi
     */
    @GetMapping("/my-orders")
    public String showMyOrders(Model model, Principal principal) {
        String username = principal.getName();
        List<Order> orders = orderService.findOrdersByUsername(username);
        model.addAttribute("orders", orders);
        return "user/my-orders";
    }

    /**
     * Xem chi tiết đơn hàng
     */
    @GetMapping("/orders/{orderId}")
    public String viewOrderDetail(@PathVariable("orderId") Integer orderId,
                                  Model model,
                                  Principal principal) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Kiểm tra quyền truy cập
        if (!order.getCustomer().getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("Bạn không có quyền xem đơn hàng này");
        }

        model.addAttribute("order", order);
        model.addAttribute("orderLines", order.getOrderLines());
        return "user/order-detail";
    }

    /**
     * Hủy đơn hàng
     */
    @PostMapping("/orders/cancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Integer orderId,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(orderId, principal.getName());
            redirectAttributes.addFlashAttribute("message", "Đã hủy đơn hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/my-orders";
    }

    /**
     * Đăng đánh giá sản phẩm
     */
    @PostMapping("/comment/post")
    public String postComment(@RequestParam("productId") Integer productId,
                              @RequestParam("text") String text,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        try {
            commentService.postComment(productId, text, principal.getName());
            redirectAttributes.addFlashAttribute("message", "Đã đăng đánh giá thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/detail/" + productId;
    }

    /**
     * Xóa đánh giá của mình
     */
    @GetMapping("/comment/delete/{commentId}")
    public String deleteMyComment(@PathVariable("commentId") Integer commentId,
                                  @RequestParam("productId") Integer productId,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        try {
            commentService.deleteComment(commentId, principal.getName(), false);
            redirectAttributes.addFlashAttribute("message", "Đã xóa đánh giá!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/detail/" + productId;
    }

    /**
     * Sửa đánh giá
     */
    @PostMapping("/comment/edit")
    public String editComment(@RequestParam("commentId") Integer commentId,
                              @RequestParam("productId") Integer productId,
                              @RequestParam("text") String text,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        try {
            commentService.updateComment(commentId, text, principal.getName());
            redirectAttributes.addFlashAttribute("message", "Đã cập nhật đánh giá!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/detail/" + productId;
    }

    /**
     * Thanh toán
     */
    @PostMapping("/checkout")
    public String checkout(@RequestParam(value = "shippingAddress", required = false) String shippingAddress,
                           @RequestParam(value = "paymentMethod", defaultValue = "COD") String paymentMethod,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();

            // Kiểm tra giỏ hàng có rỗng không
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            if (cartService.isCartEmpty(user)) {
                redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống!");
                return "redirect:/cart";
            }

            Order order = orderService.createOrderFromCart(username, shippingAddress, paymentMethod);
            redirectAttributes.addFlashAttribute("message",
                    "Đặt hàng thành công! Mã đơn hàng: #" + order.getId());
            return "redirect:/user/my-orders";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    /**
     * Xem trang checkout
     */
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (cartService.isCartEmpty(user)) {
            return "redirect:/cart";
        }

        model.addAttribute("user", user);
        model.addAttribute("customer", user.getCustomer());
        model.addAttribute("cartTotal", cartService.calculateCartTotal(user));
        return "user/checkout";
    }

    /**
     * Xem hồ sơ cá nhân
     */
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        model.addAttribute("user", user);
        model.addAttribute("customer", user.getCustomer());
        return "user/profile";
    }

    /**
     * Cập nhật hồ sơ
     */
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "address", required = false) String address,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            userService.updateProfile(user, fullName, email, phone, address);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/profile";
    }

    /**
     * Hiển thị form đổi mật khẩu
     */
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "user/change-password";
    }

    /**
     * Đổi mật khẩu
     */
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            userService.changePassword(user, oldPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công!");
            return "redirect:/user/profile";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/change-password";
        }
    }
}