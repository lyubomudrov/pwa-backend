package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.AddressResponseDto;
import com.example.pwa_shop.dto.CreateAddressRequestDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Address;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final EntityDtoMapper mapper;

    public AddressResponseDto create(CreateAddressRequestDto request, User user) {
        Address address = Address.builder()
                .city(request.city())
                .street(request.street())
                .houseNumber(request.houseNumber())
                .postalCode(request.postalCode())
                .user(user)
                .build();

        return mapper.toAddressDto(addressRepository.save(address));
    }

    public List<AddressResponseDto> getCurrentUserAddresses(User user) {
        return addressRepository.findByUserId(user.getId())
                .stream()
                .map(mapper::toAddressDto)
                .toList();
    }

    public void deleteForUser(Long addressId, User user) {
        Address address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}