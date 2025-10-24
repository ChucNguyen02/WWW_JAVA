package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}