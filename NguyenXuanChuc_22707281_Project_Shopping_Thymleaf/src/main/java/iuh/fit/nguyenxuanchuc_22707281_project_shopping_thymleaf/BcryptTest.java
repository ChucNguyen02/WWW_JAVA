package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptTest {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String plain = "password123"; // mật khẩu bạn muốn kiểm tra
        String hashFromDb = "$2a$10$kL15sLURvHMQMviH4oRQmOVkxTKNP4RdbuHMLXnM06y3W9bcdAgfO"; // thay bằng giá trị trong DB

        // 1) Kiểm tra matches
        boolean matches = encoder.matches(plain, hashFromDb);
        System.out.println("matches = " + matches);

        // 2) Sinh hash mới (BCrypt có salt ngẫu nhiên mỗi lần)
        String newHash = encoder.encode(plain);
        System.out.println("newHash = " + newHash);
        // Copy newHash vào DB nếu bạn muốn set mật khẩu password123
    }
}
