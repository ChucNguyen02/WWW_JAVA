package iuh.fit.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    int id;
    String model;
    String description;
    int quantity;
    double price;
    String imgURL;
}
