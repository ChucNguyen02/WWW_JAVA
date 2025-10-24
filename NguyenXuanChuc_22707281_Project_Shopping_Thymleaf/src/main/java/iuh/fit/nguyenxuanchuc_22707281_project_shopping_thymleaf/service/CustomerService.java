package iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.service;

import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.Customer;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.entity.User;
import iuh.fit.nguyenxuanchuc_22707281_project_shopping_thymleaf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByUser(User user) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getUser() != null && c.getUser().equals(user))
                .findFirst();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}