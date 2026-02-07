package com.georgesalise.apiRepo.api.dto;

import java.time.LocalDateTime;

public record IPInfoDetailedDTO(
        Long ipInfoId,
        String ipAddress,
        Boolean isCurrentIp,
        String country,
        String city,
        String zipcode,
        String latitude,
        String longitude,
        LocalDateTime createdAt
) {

}
