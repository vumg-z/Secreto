package com.secret.platform.category;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        return categoryOpt.orElse(null);
    }

    public Category saveCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    public List<Category> findByNameContainingIgnoreCase(String nameSubstring) {
        return categoryRepository.findByNameContainingIgnoreCase(nameSubstring);
    }


    public Category updateCategory(long id, Category updatedData) {
        if (updatedData == null) {
            throw new IllegalArgumentException("Updated data cannot be null");
        }

        // Fetch the category with the given ID from the database
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found."));

        // Update the attributes of the existing category with the attributes of updatedData
        // For the sake of this example, I'm assuming a Category has a name attribute.
        // You might have more attributes to update, adjust accordingly.
        existingCategory.setName(updatedData.getName());

        // Save the updated category back to the database
        return categoryRepository.save(existingCategory);
    }
}
