package com.secret.platform.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Example of a custom query method:
    List<Category> findByNameContainingIgnoreCase(String name);

    // Add other custom query methods if needed.
}
