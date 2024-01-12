package com.secret.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.category.Category;
import com.secret.platform.category.CategoryController;
import com.secret.platform.category.CategoryRepository;
import com.secret.platform.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static junit.framework.TestCase.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


class CategoryControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void shouldReturnCategoryWhenGetCategoryByIdIsCalled() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Sample");

        when(categoryService.getCategoryById(1L)).thenReturn(mockCategory);

        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sample"));

    }

    @Test
    void shouldReturnEmptyListWhenNoCategoriesExist() throws Exception {
        // Setup our mock service
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        // Perform the HTTP GET request and verify the response
        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(0)));
    }

    @Test
    void shouldReturnCategoryWhenCategoryIdExists() throws Exception {
        // Create a mock Category for our test
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Sample Category");

        // Setup our mock service
        when(categoryService.getCategoryById(1L)).thenReturn(mockCategory);

        // Perform the HTTP GET request and verify the response
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sample Category"));
    }

    @Test
    void shouldReturnNotFoundWhenCategoryIdDoesNotExist() throws Exception {
        // Setup our mock service
        when(categoryService.getCategoryById(1L)).thenReturn(null);  // Return null indicating the category doesn't exist

        // Perform the HTTP GET request and verify the response
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnSavedCategoryWhenValidCategoryObjectSent() throws Exception {
        // Mock Category data to be saved
        Category categoryToBeSaved = new Category();
        categoryToBeSaved.setName("Sample Category");

        // Mock Category data after being saved
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Sample Category");

        // Setup our mock service
        when(categoryService.saveCategory(any(Category.class))).thenReturn(savedCategory);

        // Perform the HTTP POST request and verify the response
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoryToBeSaved)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sample Category"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentCategory() throws Exception {
        // Create a mock Category data representing the updated data
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Sample Category");

        // Setup our mock service to return null indicating the category with the given ID doesn't exist
        when(categoryService.getCategoryById(1L)).thenReturn(null);

        // Perform the HTTP PUT request and verify the response
        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCategory)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNoContentWhenDeletingExistentCategory() throws Exception {
        // Create a mock Category data to represent an existing category
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Sample Category");

        // Setup our mock service to return the mock category, indicating that it exists
        when(categoryService.getCategoryById(1L)).thenReturn(mockCategory);
        when(categoryService.existsById(1L)).thenReturn(true);  // Mocking existsById method
        doNothing().when(categoryService).deleteCategory(1L); // Ensure the delete method does not throw any exceptions

        // Perform the HTTP DELETE request and verify the response
        mockMvc.perform(delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentCategory() throws Exception {
        // Setup our mock service to return null, indicating the category doesn't exist
        when(categoryService.getCategoryById(1L)).thenReturn(null);

        // Perform the HTTP DELETE request and verify the response
        mockMvc.perform(delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAutoGenerateTimestampsWhenCreatingCategory() throws Exception {
        Category categoryToBeSaved = new Category();
        categoryToBeSaved.setName("Sample Category");

        Category savedCategoryMock = new Category();
        savedCategoryMock.setId(1L);
        savedCategoryMock.setName("Sample Category");
        // Assuming you add setters for createdAt and updatedAt in your Category entity
        savedCategoryMock.setCreatedAt(LocalDateTime.now());
        savedCategoryMock.setUpdatedAt(LocalDateTime.now());

        when(categoryService.saveCategory(any(Category.class))).thenReturn(savedCategoryMock);

        // Use your service or controller method to save the category
        Category savedCategory = categoryService.saveCategory(categoryToBeSaved);

        assertNotNull(savedCategory.getCreatedAt());
        assertNotNull(savedCategory.getUpdatedAt());
    }

    @Test
    public void getAllCategoriesWithAllFields() throws Exception {
        // Create a mock Category for our test
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Sample Category");
        mockCategory.setDescription("Sample Description");
        mockCategory.setCreatedAt(LocalDateTime.now());
        mockCategory.setUpdatedAt(LocalDateTime.now());

        // Mock the service layer to return our mock category when getAllCategories is called
        when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(mockCategory));

        // Perform the HTTP GET request and verify the response
        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Sample Category"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"))
                .andExpect(jsonPath("$[0].createdAt").isNotEmpty()) // assuming the date gets serialized as a string or timestamp
                .andExpect(jsonPath("$[0].updatedAt").isNotEmpty());
    }




}
