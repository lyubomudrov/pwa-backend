package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.UserResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;

    public UserResponseDto create(User user) {
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