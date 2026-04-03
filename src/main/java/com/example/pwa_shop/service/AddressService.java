package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.AddressResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Address;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.AddressRepository;
import com.example.pwa_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;

    public AddressResponseDto create(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);
        return mapper.toAddressDto(addressRepository.save(address));
    }

    public List<AddressResponseDto> getByUserId(Long userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(mapper::toAddressDto)
                .toList();
    }

    public void delete(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        addressRepository.delete(address);
    }
}