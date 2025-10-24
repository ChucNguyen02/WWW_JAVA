package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Category;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Product;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        // Validate
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên sản phẩm không được để trống");
        }

        if (product.getPrice() == null || product.getPrice().doubleValue() < 0) {
            throw new RuntimeException("Giá sản phẩm không hợp lệ");
        }

        if (product.getStockQuantity() < 0) {
            throw new RuntimeException("Số lượng tồn kho không được âm");
        }

        if (product.getCategory() == null) {
            throw new RuntimeException("Phải chọn danh mục cho sản phẩm");
        }

        productRepository.save(product);
    }

    public void deleteById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Kiểm tra xem sản phẩm có trong đơn hàng nào không
        if (product.getOrderLines() != null && !product.getOrderLines().isEmpty()) {
            throw new RuntimeException("Không thể xóa sản phẩm đã có trong đơn hàng");
        }

        productRepository.deleteById(id);
    }

    /**
     * Tìm sản phẩm theo tên
     */
    public List<Product> searchByName(String keyword) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    /**
     * Tìm sản phẩm theo danh mục
     */
    public List<Product> findByCategory(Category category) {
        return productRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category))
                .toList();
    }

    /**
     * Tìm sản phẩm còn hàng
     */
    public List<Product> findInStockProducts() {
        return productRepository.findAll().stream()
                .filter(Product::isInStock)
                .toList();
    }

    /**
     * Tìm sản phẩm hết hàng
     */
    public List<Product> findOutOfStockProducts() {
        return productRepository.findAll().stream()
                .filter(p -> !p.isInStock())
                .toList();
    }

    /**
     * Cập nhật tồn kho
     */
    public void updateStock(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (quantity < 0) {
            throw new RuntimeException("Số lượng tồn kho không được âm");
        }

        product.setStockQuantity(quantity);
        productRepository.save(product);
    }

    /**
     * Tăng tồn kho
     */
    public void increaseStock(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }
}