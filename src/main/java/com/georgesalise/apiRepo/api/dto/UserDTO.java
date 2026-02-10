package com.georgesalise.apiRepo.api.dto;


import java.time.LocalDateTime;

public record UserDTO(Long userId, String email, Boolean isActive, LocalDateTime created_at) {

}
