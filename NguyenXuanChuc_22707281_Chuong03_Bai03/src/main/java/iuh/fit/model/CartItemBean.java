package iuh.fit.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemBean implements Serializable {
    Product product;
    int quantity;

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
