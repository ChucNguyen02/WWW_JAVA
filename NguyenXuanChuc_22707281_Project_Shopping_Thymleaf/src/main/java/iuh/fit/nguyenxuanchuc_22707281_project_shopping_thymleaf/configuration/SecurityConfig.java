package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.configuration;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.UserRepository;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            var authorities = user.getRoles().stream()
                    .map(role -> {
                        String name = role.getName();
                        if (!name.startsWith("ROLE_")) name = "ROLE_" + name;
                        return new SimpleGrantedAuthority(name);
                    })
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true, true, true,
                    authorities
            );
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    /**
     * Khi người dùng đăng nhập thành công, hệ thống sẽ tự động gộp giỏ hàng tạm (guest session)
     * vào giỏ hàng của tài khoản trong cơ sở dữ liệu.
     */
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(CartService cartService, UserRepository userRepository) {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            HttpSession session = request.getSession();
            var user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                cartService.mergeGuestCartToUser(session, user.get());
            }
            response.sendRedirect("/home");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider daoAuthProvider,
                                           AuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF để POST form giỏ hàng không bị chặn
                .authenticationProvider(daoAuthProvider)
                .authorizeHttpRequests(authz -> authz
                        // ✅ Cho phép toàn bộ tính năng dành cho khách (guest)
                        .requestMatchers(
                                "/", "/home", "/login", "/register", "/access-denied",
                                "/about", "/contact", // Thêm trang giới thiệu và liên hệ
                                "/css/**", "/js/**", "/images/**",
                                "/products", "/products/detail/**",
                                "/cart/**" // Cho phép mọi thao tác giỏ hàng khi chưa đăng nhập
                        ).permitAll()

                        // ✅ Quyền cho người dùng đã đăng nhập (ROLE_USER)
                        .requestMatchers("/user/**", "/my-orders").hasRole("USER")

                        // ✅ Quyền cho quản trị viên
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/perform_login")
                        .successHandler(successHandler)
                        .failureUrl("/login?error=true")
                )

                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}