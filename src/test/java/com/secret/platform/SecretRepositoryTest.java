package com.secret.platform;

import com.secret.platform.avatar.Avatar;
import com.secret.platform.category.Category;
import com.secret.platform.secret.Secret;
import com.secret.platform.secret.SecretRepository;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class SecretRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SecretRepository secretRepository;


    @Test
    public void whenPersisted_thenShouldBeAbleToFindEntity() {
        // Given
        Secret secret = new Secret();
        secret.setText("A sample secret");
        // You can set other fields here, if needed

        entityManager.persist(secret);
        entityManager.flush();

        // When
        Secret foundSecret = secretRepository.findById(secret.getId()).orElse(null);

        // Then
        Assertions.assertNotNull(foundSecret);
        Assertions.assertEquals(secret.getText(), foundSecret.getText());
    }

    @Test
    public void whenPersistedWithCategory_thenShouldBeAbleToFindEntityWithCategory() {
        LOGGER.info("Starting test: whenPersistedWithCategory_thenShouldBeAbleToFindEntityWithCategory");

        // Given
        LOGGER.info("Creating category...");
        Category category = new Category();
        category.setName("Sample Category");
        // You can set other fields of the category if required
        LOGGER.info("Persisting category...");
        entityManager.persist(category);

        LOGGER.info("Creating secret...");
        Secret secret = new Secret();
        secret.setText("A secret within a category");
        secret.setCategory(category);

        LOGGER.info("Persisting secret...");
        entityManager.persist(secret);
        LOGGER.info("Flushing...");
        entityManager.flush();

        // When
        LOGGER.info("Finding secret...");
        Secret foundSecret = secretRepository.findById(secret.getId()).orElse(null);

        // Then
        LOGGER.info("Assertions...");
        Assertions.assertNotNull(foundSecret);
        Assertions.assertEquals(secret.getText(), foundSecret.getText());
        Assertions.assertNotNull(foundSecret.getCategory());
        Assertions.assertEquals(category.getName(), foundSecret.getCategory().getName());

        LOGGER.info("Test completed: whenPersistedWithCategory_thenShouldBeAbleToFindEntityWithCategory");
    }

    @Test
    public void whenDeleted_thenShouldNotFindEntity() {
        LOGGER.info("Starting test: whenDeleted_thenShouldNotFindEntity");

        // Given
        LOGGER.info("Creating and persisting secret...");
        Secret secret = new Secret();
        secret.setText("A deletable secret");
        entityManager.persist(secret);
        entityManager.flush();

        // When
        LOGGER.info("Deleting secret...");
        secretRepository.deleteById(secret.getId());

        LOGGER.info("Attempting to find deleted secret...");
        Secret foundSecret = secretRepository.findById(secret.getId()).orElse(null);

        // Then
        LOGGER.info("Assertions...");
        Assertions.assertNull(foundSecret, "Secret should not be found after deletion");

        LOGGER.info("Test completed: whenDeleted_thenShouldNotFindEntity");
    }

    @Test
    public void whenUpdated_thenChangesShouldBeReflected() {
        LOGGER.info("Starting test: whenUpdated_thenChangesShouldBeReflected");

        // Given: Create and persist a secret
        LOGGER.info("Creating and persisting secret...");
        Secret secret = new Secret();
        secret.setText("Original secret text");
        entityManager.persist(secret);
        entityManager.flush();

        // When: Update the secret's text and persist changes
        String updatedText = "Updated secret text";
        LOGGER.info("Updating secret text...");
        secret.setText(updatedText);
        entityManager.merge(secret);
        entityManager.flush();

        LOGGER.info("Fetching the updated secret...");
        Secret updatedSecret = secretRepository.findById(secret.getId()).orElse(null);

        // Then: Check if the updated text is reflected in the fetched secret
        LOGGER.info("Assertions...");
        Assertions.assertNotNull(updatedSecret);
        Assertions.assertEquals(updatedText, updatedSecret.getText());

        LOGGER.info("Test completed: whenUpdated_thenChangesShouldBeReflected");
    }

    @Test
    public void whenSearchedByCategory_thenShouldRetrieveAssociatedSecrets() {
        LOGGER.info("Starting test: whenSearchedByCategory_thenShouldRetrieveAssociatedSecrets");

        // Given
        LOGGER.info("Creating and persisting category...");
        Category category = new Category();
        category.setName("Searchable Category");
        entityManager.persist(category);

        LOGGER.info("Creating and persisting secrets associated with the category...");
        Secret secret1 = new Secret();
        secret1.setText("First secret in the category");
        secret1.setCategory(category);
        entityManager.persist(secret1);

        Secret secret2 = new Secret();
        secret2.setText("Second secret in the category");
        secret2.setCategory(category);
        entityManager.persist(secret2);

        entityManager.flush();

        // When
        LOGGER.info("Searching secrets by category...");
        List<Secret> foundSecrets = secretRepository.findByCategory(category);

        // Then
        LOGGER.info("Assertions...");
        Assertions.assertNotNull(foundSecrets, "Found secrets should not be null");
        Assertions.assertTrue(foundSecrets.size() >= 2, "Should find at least two secrets");
        Assertions.assertTrue(foundSecrets.stream().anyMatch(s -> "First secret in the category".equals(s.getText())) &&
                foundSecrets.stream().anyMatch(s -> "Second secret in the category".equals(s.getText())), "Secret texts should match");

        LOGGER.info("Test completed: whenSearchedByCategory_thenShouldRetrieveAssociatedSecrets");
    }

    @Test
    public void whenPersistedWithAvatar_thenShouldBeAbleToFindEntityWithAvatar() {
        LOGGER.info("Starting test: whenPersistedWithAvatar_thenShouldBeAbleToFindEntityWithAvatar");

        // Given
        LOGGER.info("Creating avatar...");
        Avatar avatar = new Avatar();
        avatar.setImageUrl("http://sample-avatar-url.com/avatar1.png");
        // You can set other fields of the avatar if required
        LOGGER.info("Persisting avatar...");
        entityManager.persist(avatar);

        LOGGER.info("Creating secret with associated avatar...");
        Secret secret = new Secret();
        secret.setText("A secret with an avatar");
        secret.setAvatar(avatar);

        LOGGER.info("Persisting secret...");
        entityManager.persist(secret);
        LOGGER.info("Flushing...");
        entityManager.flush();

        // When
        LOGGER.info("Finding secret...");
        Secret foundSecret = secretRepository.findById(secret.getId()).orElse(null);

        // Then
        LOGGER.info("Assertions...");
        Assert.assertNotNull(foundSecret);
        Assert.assertEquals(secret.getText(), foundSecret.getText());
        Avatar foundAvatar = (Avatar) foundSecret.getAvatar();
        Assert.assertEquals(avatar.getImageUrl(), foundAvatar.getImageUrl());

        LOGGER.info("Test completed: whenPersistedWithAvatar_thenShouldBeAbleToFindEntityWithAvatar");
    }

    @Test
    public void whenMultipleSecretsAssociatedWithSameAvatar_thenShouldRetrieveAllAssociatedSecrets() {
        LOGGER.info("Starting test: whenMultipleSecretsAssociatedWithSameAvatar_thenShouldRetrieveAllAssociatedSecrets");

        // Given
        LOGGER.info("Creating and persisting avatar...");
        Avatar sharedAvatar = new Avatar();
        sharedAvatar.setImageUrl("http://sample-avatar-url.com/shared-avatar.png");
        entityManager.persist(sharedAvatar);

        LOGGER.info("Creating and persisting secrets associated with the shared avatar...");
        Secret secret1 = new Secret();
        secret1.setText("First secret using shared avatar");
        secret1.setAvatar(sharedAvatar);
        entityManager.persist(secret1);

        Secret secret2 = new Secret();
        secret2.setText("Second secret using shared avatar");
        secret2.setAvatar(sharedAvatar);
        entityManager.persist(secret2);

        entityManager.flush();

        // When
        LOGGER.info("Fetching secrets by avatar...");
        List<Secret> foundSecrets = secretRepository.findByAvatar(sharedAvatar);

        // Then
        LOGGER.info("Assertions...");
        Assert.assertNotNull("Found secrets should not be null", foundSecrets);
        Assert.assertTrue("Should find at least two secrets associated with the shared avatar", foundSecrets.size() >= 2);
        Assert.assertTrue("Secret texts should match",
                foundSecrets.stream().anyMatch(s -> "First secret using shared avatar".equals(s.getText())) &&
                        foundSecrets.stream().anyMatch(s -> "Second secret using shared avatar".equals(s.getText()))
        );

        LOGGER.info("Test completed: whenMultipleSecretsAssociatedWithSameAvatar_thenShouldRetrieveAllAssociatedSecrets");
    }

    @BeforeEach
    public void setUp() {
        entityManager.getEntityManager().createNativeQuery("DELETE FROM secret").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM avatar").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM category").executeUpdate();
        entityManager.flush();
    }

    @Test
    public void whenFetchingSecretsWithPagination_thenShouldReturnPaginatedResults() {
        LOGGER.info("Starting test: whenFetchingSecretsWithPagination_thenShouldReturnPaginatedResults");

        // Given: Create a number of secrets
        int totalSecrets = 50;  // You can adjust this number based on your needs
        LOGGER.info("Creating and persisting secrets...");
        for (int i = 1; i <= totalSecrets; i++) {
            Secret secret = new Secret();
            secret.setText("Secret " + i);
            entityManager.persist(secret);
        }
        entityManager.flush();

        // When: Fetch secrets using pagination (e.g., 10 secrets per page)
        int pageSize = 10;
        int pageNumber = 1;  // 0-based, so it fetches the second page
        Sort sort = Sort.by(Sort.Order.asc("orderNum"));  // Sorting by the 'order' field now;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Secret> secretsPage = secretRepository.findAll(pageRequest);

        secretsPage.getContent().forEach(secret -> LOGGER.info(secret.getText()));

        // Then: Verify the pagination results
        LOGGER.info("Assertions...");
        Assertions.assertEquals(pageSize, secretsPage.getNumberOfElements());  // Number of fetched secrets should match page size
        Assertions.assertEquals(totalSecrets, secretsPage.getTotalElements());  // Total elements should be 50
        Assertions.assertEquals(pageNumber, secretsPage.getNumber());

        Assertions.assertEquals("Secret 11", secretsPage.getContent().get(0).getText());  // As it's the second page, it should start with Secret 11 (assuming a 0-based index)

        LOGGER.info("Test completed: whenFetchingSecretsWithPagination_thenShouldReturnPaginatedResults");

        secretsPage.getContent().forEach(secret -> System.out.println(secret.getText()));
    }

    @Test
    public void whenSearchingByText_thenShouldReturnMatchingSecrets() {
        // Given: Save multiple secrets
        Secret secret1 = new Secret();
        secret1.setText("This is secret one");
        entityManager.persist(secret1);

        Secret secret2 = new Secret();
        secret2.setText("This is secret two");
        entityManager.persist(secret2);

        Secret secret3 = new Secret();
        secret3.setText("Another unrelated secret");
        entityManager.persist(secret3);
        entityManager.flush();

        // When: Search for secrets using a partial text
        List<Secret> matchedSecrets = secretRepository.findByTextContainingIgnoreCase("secret one"); // Assuming your repository has this method

        // Then: Verify the matched secrets
        Assertions.assertEquals(1, matchedSecrets.size());
        Assertions.assertEquals("This is secret one", matchedSecrets.get(0).getText());
    }

    @Test
    public void whenFetchingSecretsWithoutCategories_thenShouldReturnSecretsWithNoCategories() {
        // Given: Save multiple secrets
        Category someCategory = new Category();  // Assuming you have a Category entity
        someCategory.setName("Sample Category");
        entityManager.persist(someCategory);

        Secret secretWithCategory = new Secret();
        secretWithCategory.setText("Secret with category");
        secretWithCategory.setCategory(someCategory);
        entityManager.persist(secretWithCategory);

        Secret secretWithoutCategory1 = new Secret();
        secretWithoutCategory1.setText("First secret without category");
        entityManager.persist(secretWithoutCategory1);

        Secret secretWithoutCategory2 = new Secret();
        secretWithoutCategory2.setText("Second secret without category");
        entityManager.persist(secretWithoutCategory2);
        entityManager.flush();

        // When: Fetch secrets without categories
        List<Secret> secretsWithoutCategories = secretRepository.findByCategoryIsNull(); // Assuming your repository has this method

        // Then: Verify the fetched secrets
        Assertions.assertEquals(2, secretsWithoutCategories.size());
        Assertions.assertTrue(secretsWithoutCategories.stream().noneMatch(secret -> secret.getCategory() != null));
    }

    @Test
    public void whenDeletingSecretsAssociatedWithSpecificCategory_thenShouldRemoveThemCorrectly() {
        // Given: Save a category and associated secrets
        Category targetCategory = new Category();
        targetCategory.setName("Target Category");
        entityManager.persist(targetCategory);

        Secret secretWithTargetCategory = new Secret();
        secretWithTargetCategory.setText("Secret associated with target category");
        secretWithTargetCategory.setCategory(targetCategory);
        entityManager.persist(secretWithTargetCategory);

        Secret secretWithoutCategory = new Secret();
        secretWithoutCategory.setText("Secret without any category");
        entityManager.persist(secretWithoutCategory);

        Category anotherCategory = new Category();
        anotherCategory.setName("Another Category");
        entityManager.persist(anotherCategory);

        Secret secretWithAnotherCategory = new Secret();
        secretWithAnotherCategory.setText("Secret associated with another category");
        secretWithAnotherCategory.setCategory(anotherCategory);
        entityManager.persist(secretWithAnotherCategory);
        entityManager.flush();

        // When: Delete secrets associated with the target category
        secretRepository.deleteByCategory(targetCategory);

        // Then: Verify the results
        List<Secret> remainingSecrets = secretRepository.findAll();

        Assertions.assertTrue(remainingSecrets.stream().noneMatch(secret -> targetCategory.equals(secret.getCategory())));
        Assertions.assertTrue(remainingSecrets.stream().anyMatch(secret -> secretWithoutCategory.getText().equals(secret.getText())));
        Assertions.assertTrue(remainingSecrets.stream().anyMatch(secret -> anotherCategory.equals(secret.getCategory())));
    }

    @Test
    public void whenCountingSecretsByCategory_thenShouldReturnCorrectCounts() {
        // Given: Create categories and associated secrets
        Category categoryA = new Category();
        categoryA.setName("Category A");
        entityManager.persist(categoryA);

        Category categoryB = new Category();
        categoryB.setName("Category B");
        entityManager.persist(categoryB);

        Secret secret1ForA = new Secret();
        secret1ForA.setText("Secret 1 for Category A");
        secret1ForA.setCategory(categoryA);
        entityManager.persist(secret1ForA);

        Secret secret2ForA = new Secret();
        secret2ForA.setText("Secret 2 for Category A");
        secret2ForA.setCategory(categoryA);
        entityManager.persist(secret2ForA);

        Secret secretForB = new Secret();
        secretForB.setText("Secret for Category B");
        secretForB.setCategory(categoryB);
        entityManager.persist(secretForB);

        entityManager.flush();

        // When: Count secrets for each category
        long countForCategoryA = secretRepository.countByCategory(categoryA);
        long countForCategoryB = secretRepository.countByCategory(categoryB);

        // Then: Verify the results
        Assertions.assertEquals(2, countForCategoryA); // 2 secrets associated with category A
        Assertions.assertEquals(1, countForCategoryB); // 1 secret associated with category B
    }

}

