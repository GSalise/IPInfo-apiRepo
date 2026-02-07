package com.georgesalise.apiRepo.api.dto;


import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(Long userId, String email, LocalDateTime created_at) {

}
