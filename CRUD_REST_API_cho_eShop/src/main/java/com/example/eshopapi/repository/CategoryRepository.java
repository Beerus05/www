package com.example.eshopapi.repository;

import com.example.eshopapi.dto.CategoryStatsResponse;
import com.example.eshopapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Category> findAllByOrderByNameAsc();

    @Query("SELECT new com.example.eshopapi.dto.CategoryStatsResponse(" +
           "c.id, c.name, COUNT(p.id), COALESCE(SUM(p.price * p.quantity), 0)) " +
           "FROM Category c LEFT JOIN Product p ON p.category = c " +
           "GROUP BY c.id, c.name ORDER BY c.id ASC")
    List<CategoryStatsResponse> getCategoryStats();
}
