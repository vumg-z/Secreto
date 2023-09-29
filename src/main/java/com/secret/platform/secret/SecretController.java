package com.secret.platform.secret;

import com.secret.platform.secret.Secret;
import com.secret.platform.secret.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secrets")
public class SecretController {

    private final SecretService secretService;

    @Autowired
    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @GetMapping
    public List<Secret> getAllSecrets() {
        return secretService.getAllSecrets();
    }

    @GetMapping("/{id}")
    public Secret getSecretById(@PathVariable Long id) {
        return secretService.getSecretById(id);
    }

    @PostMapping
    public Secret saveSecret(@RequestBody Secret secret) {
        return secretService.saveSecret(secret);
    }

    @DeleteMapping("/{id}")
    public void deleteSecret(@PathVariable Long id) {
        secretService.deleteSecret(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Secret> getSecretsByCategory(@PathVariable Long categoryId) {
        return secretService.getSecretsByCategory(categoryId);
    }

    @PostMapping("/{id}/like")
    public Secret likeSecret(@PathVariable Long id) {
        return secretService.likeSecret(id);
    }

    /*
    @GetMapping("/most-liked-by-category/{categoryId}")
    public List<Secret> getMostLikedByCategory(@PathVariable Long categoryId,
                                               @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return secretService.getMostLikedSecretsByCategory(categoryId, limit);
    }
    */
}
