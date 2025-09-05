package com.api.coryfitzpatrick.unit.service;

import com.api.coryfitzpatrick.model.AppUser;
import com.api.coryfitzpatrick.repository.AppUserRepository;
import com.api.coryfitzpatrick.service.DatabaseUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DatabaseUserDetailsServiceTest {

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @MockitoBean
    private AppUserRepository appUserRepository;

    @Test
    void givenExistingUser_whenLoadUserByUsername_thenReturnsUserDetails() {
        AppUser appUser = new AppUser();
        appUser.setUsername("localUnitTestUser");
        appUser.setPassword("$2a$10$hash"); // bcrypt hash
        appUser.setRole("ADMIN");
        when(appUserRepository.findByUsername("localUnitTestUser")).thenReturn(Optional.of(appUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername("localUnitTestUser");
        assertNotNull(userDetails);
        assertEquals("localUnitTestUser", userDetails.getUsername());
        assertEquals(appUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(appUserRepository, times(1)).findByUsername("localUnitTestUser");
    }

    @Test
    void givenNonExistingUser_whenLoadUserByUsername_thenThrowsUsernameNotFoundException() {
        when(appUserRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown"));
        verify(appUserRepository, times(1)).findByUsername("unknown");
    }
}