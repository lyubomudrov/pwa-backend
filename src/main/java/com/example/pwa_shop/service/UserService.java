package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.CreateUserRequestDto;
import com.example.pwa_shop.dto.UserResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Role;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto create(CreateUserRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .role(Role.USER)
                .build();

        return mapper.toUserDto(userRepository.save(user));
    }

    public UserResponseDto getByIdDto(Long id) {
        return mapper.toUserDto(getById(id));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toUserDto)
                .toList();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}