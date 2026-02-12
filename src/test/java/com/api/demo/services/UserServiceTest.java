package com.api.demo.services;

import com.api.demo.models.UserModel;
import com.api.demo.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers_shouldReturnList() {
        // arrange
        List<UserModel> repoUsers = Arrays.asList(new UserModel(), new UserModel());
        when(userRepository.findAll()).thenReturn(repoUsers);


        List<UserModel> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser_shouldCallRepositorySave() {

        UserModel user = new UserModel();
        when(userRepository.save(user)).thenReturn(user);


        UserModel result = userService.saveUser(user);


        assertSame(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnOptional() {
        // arrange
        long id = 1L;
        UserModel user = new UserModel();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<UserModel> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertSame(user, result.get());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void updateById_shouldUpdateFieldsAndSave() {
        // arrange
        long id = 10L;

        UserModel existing = new UserModel();
        existing.setFirstName("Old");
        existing.setLastName("Name");
        existing.setEmail("old@mail.com");

        UserModel request = new UserModel();
        request.setFirstName("New");
        request.setLastName("Last");
        request.setEmail("new@mail.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(UserModel.class))).thenAnswer(inv -> inv.getArgument(0));

        // act
        UserModel result = userService.updateById(request, id);

        // assert (valida que se copiaron bien los campos)
        assertEquals("New", result.getFirstName());
        assertEquals("Last", result.getLastName());
        assertEquals("new@mail.com", result.getEmail());

        // assert (valida que se guardó el mismo objeto existente actualizado)
        ArgumentCaptor<UserModel> captor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository).save(captor.capture());
        UserModel saved = captor.getValue();

        assertSame(existing, saved);
        assertEquals("New", saved.getFirstName());
        assertEquals("Last", saved.getLastName());
        assertEquals("new@mail.com", saved.getEmail());

        verify(userRepository).findById(id);
    }

    @Test
    void updateById_whenUserNotFound_shouldThrow() {
        // arrange
        long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.updateById(new UserModel(), id));

        assertEquals("User not found", ex.getMessage());
        verify(userRepository).findById(id);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldReturnTrueWhenNoException() {
        // arrange
        long id = 5L;
        doNothing().when(userRepository).deleteById(id);

        // act
        Boolean result = userService.deleteUser(id);

        // assert
        assertTrue(result);
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_shouldReturnFalseWhenException() {
        // arrange
        long id = 6L;
        doThrow(new RuntimeException("boom")).when(userRepository).deleteById(id);

        // act
        Boolean result = userService.deleteUser(id);

        // assert
        assertFalse(result);
        verify(userRepository).deleteById(id);
    }
}
