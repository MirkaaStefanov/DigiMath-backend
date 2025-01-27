package com.example.DigiMath_backend.services.security;

import com.example.DigiMath_backend.dtos.auth.AdminUserDTO;
import com.example.DigiMath_backend.dtos.auth.PublicUserDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.enums.Role;
import com.example.DigiMath_backend.exeptions.UserCreateException;
import com.example.DigiMath_backend.exeptions.UserNotFoundException;
import com.example.DigiMath_backend.exeptions.common.AccessDeniedException;
import com.example.DigiMath_backend.models.User;
import com.example.DigiMath_backend.repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserServiceAuthentication {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User createUser(RegisterRequest request) {
        try {
            User user = User
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .usernameField(request.getUsername())
                    .deleted(false)
                    .build();

            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserCreateException(true);
        } catch (ConstraintViolationException exception) {
            throw new UserCreateException(exception.getConstraintViolations());
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email"));
    }

    @Override
    public List<AdminUserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(x -> modelMapper.map(x, AdminUserDTO.class))
                .toList();
    }

    @Override
    public AdminUserDTO updateUser(Long id, AdminUserDTO userDTO, PublicUserDTO currentUser) {
        User userToUpdate = findById(id);

        if (userToUpdate.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException();
        }

        modelMapper.map(userDTO, userToUpdate);
        userToUpdate.setId(id);

        User updatedUser = userRepository.save(userToUpdate);
        return modelMapper.map(updatedUser, AdminUserDTO.class);
    }


    @Override
    public void deleteUserById(Long id, PublicUserDTO currentUser) {
        User user = findById(id);

        if (user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException();
        }

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();

        if (user.getDateLastEntered() == null) {
            // First entry
            user.setCurrentStreak(1);
        } else {
            LocalDate lastEntered = user.getDateLastEntered();

            if (lastEntered.equals(today)) {
                // Already updated today
                return;
            } else if (lastEntered.plusDays(1).equals(today)) {
                // Continue streak
                user.setCurrentStreak(user.getCurrentStreak() + 1);
            } else {
                // Reset streak
                user.setCurrentStreak(1);
            }
        }

        // Update longest streak
        user.setLongestStreak(Math.max(user.getLongestStreak(), user.getCurrentStreak()));

        // Update last entered date
        user.setDateLastEntered(today);

        // Save user
        userRepository.save(user);

    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id"));
    }
}

