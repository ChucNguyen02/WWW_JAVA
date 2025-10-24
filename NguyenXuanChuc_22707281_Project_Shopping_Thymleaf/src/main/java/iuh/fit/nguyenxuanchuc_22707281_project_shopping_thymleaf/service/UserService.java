package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.dto.UserRegistrationDto;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Cart;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Customer;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Role;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.CartRepository;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.RoleRepository;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Đăng ký người dùng mới
     */
    public User registerNewUser(UserRegistrationDto registrationDto) {
        // Kiểm tra username đã tồn tại
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng!");
        }

        // Kiểm tra mật khẩu khớp
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp!");
        }

        // Tạo User mới
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(true);

        // Gán role USER
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role("USER");
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Tạo Customer profile ✅ ĐÃ CÓ
        Customer customer = new Customer();
        customer.setName(registrationDto.getFullName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhone(registrationDto.getPhone());
        customer.setAddress(registrationDto.getAddress());
        customer.setCustomerSince(Calendar.getInstance());
        customer.setUser(user);

        user.setCustomer(customer); // ✅ Quan trọng!

        // Lưu user (cascade sẽ tự động lưu customer)
        User savedUser = userRepository.save(user);

        // Tạo giỏ hàng cho user
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        return savedUser;
    }

    /**
     * Tìm user theo username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Tìm user theo email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Cập nhật thông tin profile
     */
    public void updateProfile(User user, String fullName, String email, String phone, String address) {
        // Kiểm tra email mới có bị trùng không
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new RuntimeException("Email đã được sử dụng bởi tài khoản khác!");
        }

        user.setEmail(email);

        if (user.getCustomer() != null) {
            user.getCustomer().setName(fullName);
            user.getCustomer().setEmail(email);
            user.getCustomer().setPhone(phone);
            user.getCustomer().setAddress(address);
        }

        userRepository.save(user);
    }

    /**
     * Đổi mật khẩu
     */
    public void changePassword(User user, String oldPassword, String newPassword, String confirmPassword) {
        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng!");
        }

        // Kiểm tra mật khẩu mới khớp
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp!");
        }

        // Kiểm tra độ dài mật khẩu mới
        if (newPassword.length() < 6) {
            throw new RuntimeException("Mật khẩu mới phải có ít nhất 6 ký tự!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Lấy tất cả users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Xóa user
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Kích hoạt/vô hiệu hóa tài khoản
     */
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    /**
     * Gán role cho user
     */
    public void assignRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role: " + roleName));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    /**
     * Xóa role khỏi user
     */
    public void removeRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role: " + roleName));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
}