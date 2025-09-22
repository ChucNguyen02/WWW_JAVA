package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {
    int id;
    String name;
    String note;
}
