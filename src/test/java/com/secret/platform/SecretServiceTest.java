package com.secret.platform;

import com.secret.platform.avatar.Avatar;
import com.secret.platform.avatar.AvatarRepository;
import com.secret.platform.secret.Secret;
import com.secret.platform.secret.SecretRepository;
import com.secret.platform.secret.SecretService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SecretServiceTest {

    @InjectMocks
    private SecretService secretService;

    @Mock
    private SecretRepository secretRepository;
    @Mock
    private AvatarRepository avatarRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllSecretsTest() {
        Secret secret1 = new Secret();
        Secret secret2 = new Secret();

        when(secretRepository.findAll()).thenReturn(Arrays.asList(secret1, secret2));

        List<Secret> result = secretService.getAllSecrets();

        assertEquals(2, result.size());
        verify(secretRepository, times(1)).findAll();
    }

    @Test
    public void getSecretByIdTest() {
        Secret secret = new Secret();
        Long id = 1L;

        when(secretRepository.findById(id)).thenReturn(Optional.of(secret));

        Secret result = secretService.getSecretById(id);

        assertNotNull(result);
        verify(secretRepository, times(1)).findById(id);
    }

    @Test
    public void saveSecretTest() {
        Secret secret = new Secret();

        // Create a mock Avatar page result
        Avatar avatar = new Avatar();
        Page<Avatar> mockPage = new PageImpl<>(List.of(avatar));

        // Mock the behavior of avatarRepository.findAll(Pageable)
        when(avatarRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        when(secretRepository.save(secret)).thenReturn(secret);

        Secret result = secretService.saveSecret(secret);

        assertNotNull(result);
        verify(secretRepository, times(1)).save(secret);
    }


    @Test
    public void deleteSecretTest() {
        Long id = 1L;
        secretService.deleteSecret(id);

        verify(secretRepository, times(1)).deleteById(id);
    }

    @Test
    public void createSecretTest() {
        // Given
        Secret secret = new Secret();
        secret.setText("Test Secret Text");
        System.out.println("Given Secret Text: " + secret.getText());

        when(avatarRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(new Avatar())));


        when(secretRepository.save(any(Secret.class))).thenAnswer(invocation -> {
            Secret savedSecret = invocation.getArgument(0);
            System.out.println("Saved Secret Text (in mock): " + savedSecret.getText());
            return savedSecret;
        });

        // When
        Secret result = secretService.saveSecret(secret);
        System.out.println("Saved Secret Text (after service call): " + result.getText());

        // Then
        assertNotNull(result);
        assertEquals("Test Secret Text", result.getText());
        System.out.println("Saved Secret Text (for assertion): " + result.getText());
        assertNotNull(result.getDateCreated());
        System.out.println("Date Created: " + result.getDateCreated());

        verify(secretRepository, times(1)).save(any(Secret.class));
    }

}
