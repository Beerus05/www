package com.example.eshopapi.service;

import com.example.eshopapi.dto.CategoryRequest;
import com.example.eshopapi.dto.CategoryResponse;
import com.example.eshopapi.dto.CategoryStatsResponse;
import com.example.eshopapi.dto.ProductResponse;
import com.example.eshopapi.entity.Category;
import com.example.eshopapi.exception.ConflictException;
import com.example.eshopapi.exception.NotFoundException;
import com.example.eshopapi.repository.CategoryRepository;
import com.example.eshopapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + id));
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name().trim())) {
            throw new ConflictException("Tên danh mục đã tồn tại");
        }

        Category category = Category.builder()
                .name(request.name().trim())
                .description(request.description())
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + id));

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.name().trim(), id)) {
            throw new ConflictException("Tên danh mục đã tồn tại");
        }

        category.setName(request.name().trim());
        category.setDescription(request.description());

        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + id));

        if (productRepository.existsByCategoryId(id)) {
            throw new ConflictException("Danh mục đang có sản phẩm, không thể xoá");
        }

        categoryRepository.delete(category);
    }

    public List<ProductResponse> getProductsByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy danh mục id = " + id);
        }

        return productRepository.findByCategoryId(id)
                .stream()
                .map(this::toProductResponse)
                .toList();
    }

    public List<CategoryStatsResponse> getCategoryStats() {
        return categoryRepository.getCategoryStats();
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public ProductResponse toProductResponse(com.example.eshopapi.entity.Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getQuantity(),
                p.getImageUrl(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getCreatedAt()
        );
    }
}
