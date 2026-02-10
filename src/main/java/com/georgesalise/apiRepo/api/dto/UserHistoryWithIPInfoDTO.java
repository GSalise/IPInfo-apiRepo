package com.georgesalise.apiRepo.api.dto;

import java.time.LocalDateTime;

public record UserHistoryWithIPInfoDTO(
        Long userHistoryId,
        String ipAddress,
        String city,
        String region,
        String country,
        String postal,
        String latitude,
        String longitude,
        Boolean isActive,
        LocalDateTime createdAt) {
}
