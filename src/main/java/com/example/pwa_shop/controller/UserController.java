package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.CreateUserRequestDto;
import com.example.pwa_shop.dto.UserResponseDto;
import com.example.pwa_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto create(@RequestBody CreateUserRequestDto request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getByIdDto(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}