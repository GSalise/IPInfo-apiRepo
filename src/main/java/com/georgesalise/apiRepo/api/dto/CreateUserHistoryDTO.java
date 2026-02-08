package com.georgesalise.apiRepo.api.dto;

import java.time.LocalDateTime;

public record CreateUserHistoryDTO (Long userId, Long ipInfoId, LocalDateTime accessedAt){
}
