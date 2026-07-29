package com.ratatouille.Ratatouille23.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    @Test
    void createUser() {
        String firstName = "Simone";
        String lastName = "Catapano";
        String email = "simdevelopercripto@gmail.com";
        String password = "yes";
        Role role = Role.SUPERVISOR;
        User user = new User(
                firstName,
                lastName,
                email,
                password,
                role
        );

        UserRequest userRequest = new UserRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        when(userRepository.save(user)).thenReturn(user);

        Long id = underTest.createUser(userRequest);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> savedUser = userRepository.findById(id);

        assertTrue(savedUser.isPresent());
        assertEquals(firstName, savedUser.get().getFirstName());
        assertEquals(lastName, savedUser.get().getLastName());
        assertEquals(email, savedUser.get().getEmail());
        assertEquals(password, savedUser.get().getPassword());
        assertEquals(role, savedUser.get().getRole());
    }




}