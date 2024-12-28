package com.example.DigiMath_backend.services.security;

import com.example.DigiMath_backend.dtos.auth.AdminUserDTO;
import com.example.DigiMath_backend.dtos.auth.PublicUserDTO;
import com.example.DigiMath_backend.dtos.auth.RegisterRequest;
import com.example.DigiMath_backend.models.User;

import java.util.List;

public interface UserServiceAuthentication {
    User createUser(RegisterRequest request);

    User findByEmail(String email);

    List<AdminUserDTO> getAllUsers();

    AdminUserDTO updateUser(Long id, AdminUserDTO userDTO, PublicUserDTO currentUser);

    void deleteUserById(Long id, PublicUserDTO currentUser);
}
