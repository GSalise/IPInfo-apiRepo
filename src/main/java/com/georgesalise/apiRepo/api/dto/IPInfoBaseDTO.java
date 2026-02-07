package com.georgesalise.apiRepo.api.dto;

public record IPInfoBaseDTO(
        Long ipInfoId,
        String ipAddress,
        Boolean isCurrentIp,
        String country
) {
}
