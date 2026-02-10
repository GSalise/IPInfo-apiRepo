package com.georgesalise.apiRepo.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @Setter
    @Getter
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "ipinfo_id", nullable = false)
    private IPInfo ipInfo;

    @Getter
    @Setter
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Getter
    @Setter
    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    public Long getUserId() {
        return user != null ? user.getUserId() : null;  // Get ID from User object
    }

    public Long getIpInfoId() {
        return ipInfo != null ? ipInfo.getIpInfoId() : null;  // Get ID from IPInfo object
    }
}
