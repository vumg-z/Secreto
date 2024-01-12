package com.secret.platform.secret;

import com.secret.platform.avatar.Avatar;
import com.secret.platform.avatar.AvatarRepository;
import com.secret.platform.category.Category;
import com.secret.platform.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecretService {

    private final SecretRepository secretRepository;
    private final AvatarRepository avatarRepository;  // Inject the AvatarRepository

    @Autowired
    public SecretService(SecretRepository secretRepository, CategoryRepository categoryRepository, AvatarRepository avatarRepository) {
        this.secretRepository = secretRepository;
        this.avatarRepository = avatarRepository;
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

            // Choose an avatar and set it. This is a basic example that would need to be modified based on your needs.
            Avatar avatar = chooseAvatarForSecret();
            secret.setAvatar(avatar);
        }

        return secretRepository.save(secret);
    }

    private Avatar chooseAvatarForSecret() {
        // For example, fetch the first avatar (you might want a different logic)
        return avatarRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
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


}
