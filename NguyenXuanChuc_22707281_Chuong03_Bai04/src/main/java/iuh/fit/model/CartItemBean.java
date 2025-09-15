package iuh.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemBean {
    private Book book;
    private int quantity;

    public double getSubtotal() {
        return book.getPrice() * quantity;
    }
}
