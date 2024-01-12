package com.secret.platform;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.secret.platform.category.Category;
import com.secret.platform.category.CategoryRepository;
import com.secret.platform.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void sanityCheckGetCategoryById() {
        // Arrange
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Sample Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(expectedCategory));

        // Act
        Category returnedCategory = categoryService.getCategoryById(1L);

        // Assert
        assertEquals(expectedCategory, returnedCategory);
    }

    @Test
    void getCategoryByIdNonExistence() {
        // Description: If a category does not exist with a given ID, the service should return null.

        // Setup: Mock the repository's findById method to return an Optional.empty().
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Category returnedCategory = categoryService.getCategoryById(1L);

        // Expected Behavior: The service returns null.
        assertNull(returnedCategory);
    }

    @Test
    void getAllCategories() {
        // Description: Test if the service correctly fetches all categories.

        // Setup: Mock the repository's findAll method to return a list of categories.
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Sample Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Sample Category 2");

        List<Category> mockCategoryList = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(mockCategoryList);

        // Act
        List<Category> returnedCategories = categoryService.getAllCategories();

        // Expected Behavior: The service should return a list with the expected categories.
        assertEquals(mockCategoryList, returnedCategories);
    }

    @Test
    void saveCategory() {
        // Description: Ensure the service can save a new category.

        // Setup: Mock the repository's save method to return the given category.
        Category newCategory = new Category();
        newCategory.setName("New Sample Category");

        when(categoryRepository.save(newCategory)).thenReturn(newCategory);

        // Act
        Category returnedCategory = categoryService.saveCategory(newCategory);

        // Expected Behavior: The returned category from the service should match the saved category.
        assertEquals(newCategory, returnedCategory);
    }

    @Test
    void deleteCategory() {
        // Description: Ensure the service can delete a category by its ID.

        Long categoryIdToDelete = 1L;

        // Act
        categoryService.deleteCategory(categoryIdToDelete);

        // Expected Behavior: The deleteById method of the repository should be called with the correct ID.
        verify(categoryRepository, times(1)).deleteById(categoryIdToDelete);
    }

    @Test
    void categoryExistenceCheckExists() {
        // Description: Ensure the service correctly checks the existence of a category.

        Long categoryId = 1L;

        // Setup: Mock the repository's existsById method to return true.
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Act
        boolean exists = categoryService.existsById(categoryId);

        // Expected Behavior: The service should return true.
        assertTrue(exists);
    }

    @Test
    void categoryExistenceCheckDoesntExist() {
        // Description: Ensure the service correctly checks the non-existence of a category.

        Long categoryId = 1L;

        // Setup: Mock the repository's existsById method to return false.
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act
        boolean exists = categoryService.existsById(categoryId);

        // Expected Behavior: The service should return false.
        assertFalse(exists);
    }

    @Test
    void getCategoriesByNameMatchFound() {
        // Description: Ensure the service can fetch categories by a name substring.

        String nameSubstring = "Sample";

        // Setup: Mock the repository's findByNameContainingIgnoreCase to return a list of matching categories.
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Sample Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Sample Category 2");

        List<Category> mockCategoryList = Arrays.asList(category1, category2);

        when(categoryRepository.findByNameContainingIgnoreCase(nameSubstring)).thenReturn(mockCategoryList);

        // Act
        List<Category> returnedCategories = categoryService.findByNameContainingIgnoreCase(nameSubstring);

        // Expected Behavior: The service should return a list of categories that match the name substring.
        assertEquals(mockCategoryList, returnedCategories);
    }

    @Test
    void getCategoriesByNameNoMatch() {
        // Description: If no categories match the name substring, ensure the service returns an empty list.

        String nameSubstring = "NonExistentName";

        // Setup: Mock the repository's findByNameContainingIgnoreCase method to return an empty list.
        when(categoryRepository.findByNameContainingIgnoreCase(nameSubstring)).thenReturn(Collections.emptyList());

        // Act
        List<Category> returnedCategories = categoryService.findByNameContainingIgnoreCase(nameSubstring);

        // Expected Behavior: The service should return an empty list.
        assertTrue(returnedCategories.isEmpty());
    }

    @Test
    void saveCategoryNullInput() {
        // Description: Ensure that the service handles null inputs gracefully when trying to save a category.

        // Setup: Call the save method with a null category.
        Category nullCategory = null;

        // Expected Behavior: The service might throw an appropriate exception.
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.saveCategory(nullCategory);
        });

        // Ensure that the save method of the repository was never called with a null category.
        verify(categoryRepository, times(0)).save(nullCategory);
    }

    @Test
    void saveCategoryDuplicateName() {
        // Setup: Create a category and assume it's already in the database.
        Category existingCategory = new Category();
        existingCategory.setName("Existing Name");

        when(categoryRepository.save(existingCategory)).thenThrow(new DataIntegrityViolationException("Duplicate name"));

        // Expected Behavior: The service should throw a DataIntegrityViolationException.
        assertThrows(DataIntegrityViolationException.class, () -> {
            categoryService.saveCategory(existingCategory);
        });
    }

    @Test
    void updateCategorySuccessfully() {
        // Setup: Existing category in the database.
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Old Name");

        // New data to update.
        Category updatedData = new Category();
        updatedData.setName("New Name");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedData);


        // Act
        Category result = categoryService.updateCategory(1L, updatedData);

        // Assert: Verify the updated name.
        assertEquals("New Name", result.getName());
    }
}

