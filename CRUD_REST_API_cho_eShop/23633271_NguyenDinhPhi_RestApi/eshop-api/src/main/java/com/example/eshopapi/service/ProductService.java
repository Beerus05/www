package com.example.eshopapi.service;

import com.example.eshopapi.dto.PageResponse;
import com.example.eshopapi.dto.ProductRequest;
import com.example.eshopapi.dto.ProductResponse;
import com.example.eshopapi.entity.Category;
import com.example.eshopapi.entity.Product;
import com.example.eshopapi.exception.ConflictException;
import com.example.eshopapi.exception.NotFoundException;
import com.example.eshopapi.repository.CategoryRepository;
import com.example.eshopapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id", "name", "price", "quantity", "createdAt"
    );

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public PageResponse<ProductResponse> getProducts(
            String keyword,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sort
    ) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        String trimmedKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        Page<Product> productPage = productRepository.search(
                trimmedKeyword,
                categoryId,
                minPrice,
                maxPrice,
                pageable
        );

        List<ProductResponse> content = productPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm id = " + id));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + request.categoryId()));

        Product product = Product.builder()
                .name(request.name().trim())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .imageUrl(request.imageUrl())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm id = " + id));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + request.categoryId()));

        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setImageUrl(request.imageUrl());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm id = " + id));

        productRepository.delete(product);
    }

    @Transactional
    public ProductResponse updateStock(Long id, int delta) {
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm id = " + id));

        int newQuantity = product.getQuantity() + delta;
        if (newQuantity < 0) {
            throw new ConflictException("Số lượng tồn kho không đủ (tồn kho sau khi trừ < 0)");
        }

        product.setQuantity(newQuantity);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getCreatedAt()
        );
    }

    private Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.trim().isEmpty()) {
            return Sort.by(Sort.Direction.ASC, "id");
        }

        List<Sort.Order> orders = new ArrayList<>();
        String[] parts = sortParam.split(";");
        for (String part : parts) {
            if (part.isBlank()) continue;
            String[] tokens = part.split(",");
            String property = tokens[0].trim();

            if (!ALLOWED_SORT_FIELDS.contains(property)) {
                throw new IllegalArgumentException("Thuộc tính sắp xếp không hợp lệ: " + property);
            }

            Sort.Direction direction = Sort.Direction.ASC;
            if (tokens.length > 1 && tokens[1].trim().equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
            orders.add(new Sort.Order(direction, property));
        }

        return orders.isEmpty() ? Sort.by(Sort.Direction.ASC, "id") : Sort.by(orders);
    }
}
