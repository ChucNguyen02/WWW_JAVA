package iuh.fit.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartBean implements Serializable {
    List<CartItemBean> itemBeans = new ArrayList<>();

    public List<CartItemBean> getItems(){
        return itemBeans;
    }

    public void addProduct(Product p){
        itemBeans.stream()
                .filter(item -> item.getProduct().getId() == p.getId())
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                                () -> itemBeans.add(new CartItemBean(p, 1))
                );
    }

    public void removeProduct(int productId){
        itemBeans.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void updateQuantity(int productId, int quantity){
        itemBeans.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .ifPresent(item -> {
                    if(quantity > 0){
                        item.setQuantity(quantity);
                    } else {
                        removeProduct(productId);
                    }
                });
    }

    public void clear() {
        itemBeans.clear();
    }

    public double getTotal() {
        return itemBeans.stream().mapToDouble(CartItemBean::getSubtotal).sum();
    }
}
