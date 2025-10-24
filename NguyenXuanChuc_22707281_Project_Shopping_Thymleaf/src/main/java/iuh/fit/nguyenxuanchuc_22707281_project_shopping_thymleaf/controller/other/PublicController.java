package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.controller.other;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Product;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.CategoryService;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PublicController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Trang chủ
     */
    @GetMapping({"/", "/home"})
    public String showHomePage(Model model) {
        // Hiển thị một số sản phẩm nổi bật
        List<Product> products = productService.findAll();
        if (products.size() > 8) {
            products = products.subList(0, 8);
        }
        model.addAttribute("featuredProducts", products);
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    /**
     * Trang đăng nhập
     */
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đăng xuất thành công!");
        }
        return "login";
    }

    /**
     * Trang truy cập bị từ chối
     */
    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    /**
     * Danh sách sản phẩm
     */
    @GetMapping("/products")
    public String showProductList(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  @RequestParam(value = "search", required = false) String search,
                                  Model model) {
        List<Product> products;

        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchByName(search);
            model.addAttribute("search", search);
        } else if (categoryId != null) {
            products = categoryService.findById(categoryId)
                    .map(productService::findByCategory)
                    .orElse(productService.findAll());
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            products = productService.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        return "products/list";
    }

    /**
     * Chi tiết sản phẩm
     */
    @GetMapping("/products/detail/{id}")
    public String showProductDetail(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + id));

        model.addAttribute("product", product);
        model.addAttribute("comments", product.getComments());

        // Sản phẩm liên quan (cùng danh mục)
        List<Product> relatedProducts = productService.findByCategory(product.getCategory())
                .stream()
                .filter(p -> !p.getId().equals(id))
                .limit(4)
                .toList();
        model.addAttribute("relatedProducts", relatedProducts);

        return "products/detail";
    }

    /**
     * Trang giới thiệu
     */
    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    /**
     * Trang liên hệ
     */
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }
}