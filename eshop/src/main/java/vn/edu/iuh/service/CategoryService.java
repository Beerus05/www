package vn.edu.iuh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.entity.Category;
import vn.edu.iuh.exception.NotFoundException;
import vn.edu.iuh.repository.CategoryRepository;
import vn.edu.iuh.repository.ProductRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục id = " + id));
    }
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    public void delete(Long id) {
        if (productRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("Danh mục đang có sản phẩm, không thể xoá");
        }
        categoryRepository.deleteById(id);
    }
}