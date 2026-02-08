package com.georgesalise.apiRepo.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ipinfo")
public class IPInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "ipinfo_id")
    private Long ipInfoId;

    @Setter
    @Getter
    @Column(name = "ip_address", length = 15, nullable = false, unique = true)
    private String ipAddress;

    @Setter
    @Getter
    @Column(name = "city", nullable = false)
    private String city;

    @Setter
    @Getter
    @Column(name = "region", nullable = false)
    private String region;

    @Setter
    @Getter
    @Column(name = "country", nullable = false)
    private String country;

    @Setter
    @Getter
    @Column(name = "postal", nullable = false)
    private String postal;

    @Setter
    @Getter
    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Setter
    @Getter
    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Setter
    @Getter
    @Column(name = "is_current_ip", nullable = false)
    private Boolean isCurrentIp = false;

    @Getter
    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @Setter
    @OneToMany(mappedBy = "ipInfo", cascade = CascadeType.ALL)
    private List<UserHistory> userHistory = new ArrayList<>();
}
