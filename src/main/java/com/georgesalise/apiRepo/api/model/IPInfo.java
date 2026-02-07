package com.georgesalise.apiRepo.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ipinfo")
public class IPInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipInfo_id")
    private Long ipInfoId;

    @Column(name = "ip_address", length = 15, nullable = false, unique = true)
    private String ipAddress;

    @Column(name = "is_current_ip", nullable = false)
    private Boolean isCurrentIp = false;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ipInfo", cascade = CascadeType.ALL)
    private List<UserHistory> userHistory = new ArrayList<>();

    public Long getIpInfoId() {
        return ipInfoId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getIsCurrentIp() {
        return isCurrentIp;
    }

    public void setIsCurrentIp(Boolean isCurrentIp) {
        this.isCurrentIp = isCurrentIp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserHistory> getUserHistory() {
        return userHistory;
    }

    public void setUser_history(List<UserHistory> userHistory) {
        this.userHistory = userHistory;
    }
}
