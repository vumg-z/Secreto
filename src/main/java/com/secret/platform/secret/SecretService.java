package com.secret.platform.secret;

import com.secret.platform.category.Category;
import com.secret.platform.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecretService {

    private final SecretRepository secretRepository;

    @Autowired
    public SecretService(SecretRepository secretRepository, CategoryRepository categoryRepository) {
        this.secretRepository = secretRepository;
    }

    public List<Secret> getAllSecrets() {
        return secretRepository.findAll();
    }

    public Secret getSecretById(Long id) {
        return secretRepository.findById(id).orElse(null);
    }

    public Secret saveSecret(Secret secret) {
        if (secret.getId() == null) {  // Indicates it's a new Secret
            secret.setDateCreated(LocalDateTime.now());
        }
        return secretRepository.save(secret);
    }

    public void deleteSecret(Long id) {
        secretRepository.deleteById(id);
    }

    public List<Secret> getSecretsByCategory(Long categoryId) {
        return secretRepository.findByCategoryId(categoryId);
    }

    public Secret likeSecret(Long id) {
        Secret secret = getSecretById(id);
        if (secret == null) {
            throw new IllegalArgumentException("Secret not found for id: " + id);
        }
        secret.incrementLikes();
        return secretRepository.save(secret);
    }

    /*
    public List<Secret> getMostLikedSecretsByCategory(Long categoryId, int limit) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found for id: " + categoryId));
        Pageable pageable = (Pageable) PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "likesCount"));
        return secretRepository.findByCategoryOrderByLikesCountDesc(category, pageable);
    }
*/
}
