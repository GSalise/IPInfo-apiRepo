package com.georgesalise.apiRepo.api.dto;

import java.time.LocalDateTime;

public record UserHistoryDTO(Long historyId, Long userId, Long ipInfoId, LocalDateTime accessedAt) {

}
