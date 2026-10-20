package vn.edu.iuh.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.entity.Category;

import java.util.List;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByNameAsc();
}