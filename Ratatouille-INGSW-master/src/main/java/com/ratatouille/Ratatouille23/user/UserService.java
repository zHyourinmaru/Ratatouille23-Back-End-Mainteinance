package com.ratatouille.Ratatouille23.user;

import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.order.OrderRepository;
import com.ratatouille.Ratatouille23.order.OrderResponse;
import com.ratatouille.Ratatouille23.order.OrderResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserResponseMapper userResponseMapper;

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;

    public List<UserResponse> getUsers() {
        return repository.findAll()
                .stream()
                .map(userResponseMapper).collect(Collectors.toList());
    }

    public UserResponse getById(Long id) {
        return repository
                .findById(id)
                .map(userResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with id " + id + " does not exists!"));
    }

    public UserResponse getByFirstName(String firstName) {
        return repository
                .findByFirstName(firstName)
                .map(userResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with firstName " + firstName + " does not exists!"));
    }

    public UserResponse getByLastName(String lastName) {
        return repository
                .findByLastName(lastName)
                .map(userResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with firstName " + lastName + " does not exists!"));
    }

    public UserResponse getByEmail(String email) {
        return repository
                .findByEmail(email)
                .map(userResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with firstName " + email + " does not exists!"));
    }

    public List<UserResponse> getUsersByAttributes(Long id, String firstName, String lastName, String email, Role role) {
        if (id != null) {
            return Collections.singletonList(getById(id));
        }
        return repository.searchUsers(firstName, lastName, email, role)
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(userResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() ->  new ApiRequestException(HttpStatus.NOT_FOUND, "No user with this criteria found!"));

    }

    public Long countUsers(Long id, String firstName, String lastName, String email, Role role) {
        if (id != null) {
            return Stream.of(getById(id)).count();
        }
        return repository.countUsers(firstName, lastName, email, role)
                .orElseThrow(() ->  new ApiRequestException(HttpStatus.NOT_FOUND, "No user with this criteria found!"));
    }

    public Long createUser(UserRequest userRequest) {
        Optional<User> userOptional = repository.findByEmail(userRequest.email());
        if (userOptional.isPresent()) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"Email taken!");
        }
        String password = userRequest.password();
        if (password == null) {
            password = "defaultPassword";
        }
        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(password)
                .role(userRequest.role())
                .build();
        return repository.save(user).getId();
    }
    public void deleteUser(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "User with id " + id + " does not exists!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, UserRequest request) {
        User user = repository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with id " + id + " does not exists!"));
        String firstName = request.firstName();
        String lastName = request.lastName();
        String email = request.email();
        Role role = request.role();

        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userOptional = repository.findByEmail(email);
            if (userOptional.isPresent()) {
                throw new ApiRequestException(HttpStatus.BAD_REQUEST, "email taken");
            }
            user.setEmail(email);
        }

        if (role != Role.ADMIN && role != user.getRole() && user.getRole() != Role.ADMIN) {
            user.setRole(role);
        }
    }

    public List<OrderResponse> getOrderByUser(Long id) {
        return orderRepository.findAllByUserId(id)
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(orderResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "This user has no orders!"));
    }
}
