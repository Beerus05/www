package com.example.eshopapi.controller;

import com.example.eshopapi.dto.CategoryRequest;
import com.example.eshopapi.dto.CategoryResponse;
import com.example.eshopapi.dto.CategoryStatsResponse;
import com.example.eshopapi.dto.ProductResponse;
import com.example.eshopapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/stats")
    public ResponseEntity<List<CategoryStatsResponse>> getCategoryStats() {
        return ResponseEntity.ok(categoryService.getCategoryStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse created = categoryService.createCategory(request);
        URI location = URI.create("/api/categories/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategoryId(id));
    }
}
