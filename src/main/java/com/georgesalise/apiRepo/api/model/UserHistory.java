package com.georgesalise.apiRepo.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "ipInfo_id", nullable = false)
    private IPInfo ipInfo;

    @Column(name = "ipInfo_id", insertable = false, updatable = false)
    private Long ipInfoId;

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    public Long getHistoryId() {
        return historyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IPInfo getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(IPInfo ipInfo) {
        this.ipInfo = ipInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIpInfoId() {
        return ipInfoId;
    }

    public void setIpInfoId(Long ipInfoId) {
        this.ipInfoId = ipInfoId;
    }

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }
}
