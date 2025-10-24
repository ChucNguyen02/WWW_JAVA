package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.controller.admin;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.*;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * Dashboard
     */
    @GetMapping
    public String showAdminDashboard(Model model) {
        // Thống kê
        long totalProducts = productService.findAll().size();
        long totalCustomers = customerService.findAll().size();
        long totalOrders = orderService.findAll().size();

        Map<Order.OrderStatus, Long> orderStats = orderService.countOrdersByStatus();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("orderStats", orderStats);

        return "admin/dashboard";
    }

    // ==================== PRODUCTS ====================

    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products-manage";
    }

    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("categoryId") Integer categoryId,
                              RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
            product.setCategory(category);
            productService.save(product);
            redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/products";
    }

    /**
     * Cập nhật tồn kho
     */
    @PostMapping("/products/update-stock")
    public String updateStock(@RequestParam("productId") Integer productId,
                              @RequestParam("quantity") Integer quantity,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.updateStock(productId, quantity);
            redirectAttributes.addFlashAttribute("message", "Cập nhật tồn kho thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/products";
    }

    // ==================== CATEGORIES ====================

    @GetMapping("/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories-manage";
    }

    @GetMapping("/categories/new")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") int id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category,
                               RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("message", "Lưu danh mục thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id,
                                 RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Không thể xóa danh mục có sản phẩm liên quan!");
        }
        return "redirect:/admin/categories";
    }

    // ==================== CUSTOMERS ====================

    @GetMapping("/customers")
    public String manageCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin/customers-manage";
    }

    @GetMapping("/customers/view/{id}")
    public String viewCustomerDetail(@PathVariable("id") int id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orderService.findOrdersByCustomer(customer));
        return "admin/customer-detail";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable("id") int id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        model.addAttribute("customer", customer);
        return "admin/customer-form";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer,
                               RedirectAttributes redirectAttributes) {
        try {
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "Lưu thông tin khách hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id,
                                 RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Không thể xóa khách hàng có đơn hàng liên quan!");
        }
        return "redirect:/admin/customers";
    }

    // ==================== ORDERS ====================

    @GetMapping("/orders")
    public String manageOrders(Model model,
                               @RequestParam(value = "status", required = false) String status) {
        if (status != null && !status.isEmpty()) {
            // Lọc theo trạng thái
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
            model.addAttribute("orders", orderService.findAll().stream()
                    .filter(o -> o.getStatus() == orderStatus)
                    .toList());
            model.addAttribute("filterStatus", status);
        } else {
            model.addAttribute("orders", orderService.findAll());
        }
        model.addAttribute("orderStatuses", Order.OrderStatus.values());
        return "admin/orders-manage";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetailAdmin(@PathVariable("id") int id, Model model) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        model.addAttribute("order", order);
        model.addAttribute("orderLines", order.getOrderLines());
        model.addAttribute("orderStatuses", Order.OrderStatus.values());
        return "admin/order-detail";
    }

    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("status") Order.OrderStatus status,
                                    RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, status);
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/orders/" + orderId;
    }

    // ==================== COMMENTS ====================

    @GetMapping("/comments")
    public String manageComments(Model model) {
        // Lấy tất cả comments từ tất cả sản phẩm
        model.addAttribute("products", productService.findAll());
        return "admin/comments-manage";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") int commentId,
                                @RequestParam("returnUrl") String returnUrl,
                                RedirectAttributes redirectAttributes) {
        try {
            commentService.deleteCommentById(commentId);
            redirectAttributes.addFlashAttribute("message", "Xóa đánh giá thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:" + returnUrl;
    }

    // ==================== USERS ====================

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users-manage";
    }

    @PostMapping("/users/toggle-status/{id}")
    public String toggleUserStatus(@PathVariable("id") Long userId,
                                   RedirectAttributes redirectAttributes) {
        try {
            userService.toggleUserStatus(userId);
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái tài khoản thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
}