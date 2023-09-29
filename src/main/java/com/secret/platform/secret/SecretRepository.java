package com.secret.platform.secret;

import com.secret.platform.avatar.Avatar;
import com.secret.platform.category.Category;
import com.secret.platform.secret.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecretRepository extends JpaRepository<Secret, Long> {
    List<Secret> findByCategory(Category category);


    List<Secret> findByAvatar(Avatar sharedAvatar);

    List<Secret> findByTextContainingIgnoreCase(String text);

    List<Secret> findByCategoryIsNull();

    void deleteByCategory(Category category);

    long countByCategory(Category categoryA);

    List<Secret> findByCategoryId(Long categoryId);
}
