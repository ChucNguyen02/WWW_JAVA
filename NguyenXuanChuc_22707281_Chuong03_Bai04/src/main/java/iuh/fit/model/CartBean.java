package iuh.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartBean implements Serializable {
    private List<CartItemBean> items = new ArrayList<>();

    public void addBook(Book b) {
        items.stream()
                .filter(item -> item.getBook().getId() == b.getId())
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> items.add(new CartItemBean(b, 1))
                );
    }

    public void updateQuantity(int bookId, int quantity) {
        items.stream()
                .filter(item -> item.getBook().getId() == bookId)
                .findFirst()
                .ifPresent(item -> {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                    } else {
                        removeBook(bookId);
                    }
                });
    }

    public void removeBook(int bookId) {
        items.removeIf(item -> item.getBook().getId() == bookId);
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItemBean::getSubtotal).sum();
    }
}
