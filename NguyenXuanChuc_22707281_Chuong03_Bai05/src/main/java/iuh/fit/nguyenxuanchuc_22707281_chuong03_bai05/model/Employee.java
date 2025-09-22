package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
     int id;
     String name;
     String email;
     String phone;
     String position;
     BigDecimal salary;
     int departmentId;
}
