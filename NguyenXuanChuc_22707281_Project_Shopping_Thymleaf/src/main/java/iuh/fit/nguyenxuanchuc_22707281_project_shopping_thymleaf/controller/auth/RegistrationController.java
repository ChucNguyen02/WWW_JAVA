package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.controller.auth;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.dto.UserRegistrationDto;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller xử lý đăng ký tài khoản mới.
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    /**
     * Hiển thị form đăng ký.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "auth/register";
    }

    /**
     * Xử lý đăng ký tài khoản.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            // Đăng ký user mới
            userService.registerNewUser(registrationDto);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }
}