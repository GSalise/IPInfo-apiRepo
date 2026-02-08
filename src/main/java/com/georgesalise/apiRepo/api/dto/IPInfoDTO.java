package com.georgesalise.apiRepo.api.dto;

import java.time.LocalDateTime;

public record IPInfoDTO(
        Long ipInfoId,
        String ipAddress,
        String city,
        String region,
        String country,
        String postal,
        String latitude,
        String longitude,
        Boolean isCurrentIp,
        LocalDateTime createdAt
) {

}
