package com.georgesalise.apiRepo.api.dto;

public record IPGeoAPIDTO(
        String ip,
        String city,
        String region,
        String country,
        String loc,
        String postal) {

    public String getLatitude() {
        if (loc == null || !loc.contains(",")) return "0.0";
        return loc.split(",")[0].trim();
    }

    public String getLongitude() {
        if (loc == null || !loc.contains(",")) return "0.0";
        return loc.split(",")[1].trim();
    }
}
