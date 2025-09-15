package iuh.fit.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    int id;
    String title;
    String author;
    double price;
    int quantity;
    String imgURL;
    String description;
}