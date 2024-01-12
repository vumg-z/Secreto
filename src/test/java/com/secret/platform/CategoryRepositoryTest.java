package com.secret.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.category.Category;
import com.secret.platform.category.CategoryController;
import com.secret.platform.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CategoryRepositoryTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setName("Sample Category");
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category));

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Sample Category", response.getBody().get(0).getName());
    }

    @Test
    public void testGetCategoryById_found() {
        Category category = new Category();
        category.setId(1L);
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetCategoryById_notFound() {
        when(categoryService.getCategoryById(1L)).thenReturn(null);

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateCategory() {
        // Given
        Category categoryToCreate = new Category();
        categoryToCreate.setName("Sample Category");

        Category savedCategory = new Category();
        savedCategory.setName("Sample Category");
        savedCategory.setId(1L);

        // Mocking the service behavior
        when(categoryService.saveCategory(categoryToCreate)).thenReturn(savedCategory);

        // When
        ResponseEntity<Category> response = categoryController.createCategory(categoryToCreate);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedCategory.getId(), response.getBody().getId());
        assertEquals(savedCategory.getName(), response.getBody().getName());

        verify(categoryService, times(1)).saveCategory(categoryToCreate);
    }

    @Test
    public void testUpdateCategory_Success() {
        // Given
        Long categoryId = 1L;
        Category categoryToUpdate = new Category();
        categoryToUpdate.setName("Original Category");
        categoryToUpdate.setId(categoryId);

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");
        updatedCategory.setId(categoryId);

        // Mocking the service behavior
        when(categoryService.saveCategory(categoryToUpdate)).thenReturn(updatedCategory);

        // When
        ResponseEntity<Category> response = categoryController.updateCategory(categoryId, categoryToUpdate);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedCategory.getId(), response.getBody().getId());
        assertEquals(updatedCategory.getName(), response.getBody().getName());

        verify(categoryService, times(1)).saveCategory(categoryToUpdate);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        // Given
        Long nonExistentCategoryId = 99L; // an ID that doesn't exist in our mock setup
        Category categoryToUpdate = new Category();
        categoryToUpdate.setName("Non-Existent Category");
        categoryToUpdate.setId(nonExistentCategoryId);

        // Mocking the service behavior to return null
        when(categoryService.saveCategory(categoryToUpdate)).thenReturn(null);

        // When
        ResponseEntity<Category> response = categoryController.updateCategory(nonExistentCategoryId, categoryToUpdate);

        // Then
        assertEquals(404, response.getStatusCodeValue());

        verify(categoryService, times(1)).saveCategory(categoryToUpdate);
    }

    @Test
    public void testDeleteCategory_Success() {
        // Given
        Long categoryId = 1L;

        // Mocking the service behavior for checking existence and deletion
        when(categoryService.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryService).deleteCategory(categoryId);

        // When
        ResponseEntity<Void> response = categoryController.deleteCategory(categoryId);

        // Then
        assertEquals(204, response.getStatusCodeValue());

        verify(categoryService, times(1)).existsById(categoryId);
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        // Given
        Long nonExistentCategoryId = 99L; // an ID that doesn't exist

        // Mocking the service behavior to indicate the category doesn't exist
        when(categoryService.existsById(nonExistentCategoryId)).thenReturn(false);

        // When
        ResponseEntity<Void> response = categoryController.deleteCategory(nonExistentCategoryId);

        // Then
        assertEquals(404, response.getStatusCodeValue());

        verify(categoryService, times(1)).existsById(nonExistentCategoryId);
        verify(categoryService, never()).deleteCategory(anyLong()); // Ensure the delete method is never called
    }

    @Test
    public void testCategoryCreationValidation() throws Exception {
        Category invalidCategory = new Category();
        invalidCategory.setName("");  // Empty name

        mockMvc.perform(post("/categories") // Use the correct path here
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidCategory)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testServiceLayerExceptionHandling() throws Exception {
        CategoryService categoryService = mock(CategoryService.class);
        when(categoryService.saveCategory(any())).thenThrow(new RuntimeException("Service error message"));

        CategoryController categoryController = new CategoryController(categoryService);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();


        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Category\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error message"));
    }
}
