package com.example.antivirusbackend.dto;

import java.time.LocalDateTime;

public class Ticket {
    private LocalDateTime serverDate;
    private long lifeTimeSeconds;
    private LocalDateTime activationDate;
    private LocalDateTime endingDate;
    private Long userId;
    private String deviceId;
    private boolean blocked;
    private String digitalSignature;

    public LocalDateTime getServerDate() { return serverDate; }
    public void setServerDate(LocalDateTime serverDate) { this.serverDate = serverDate; }

    public long getLifeTimeSeconds() { return lifeTimeSeconds; }
    public void setLifeTimeSeconds(long lifeTimeSeconds) { this.lifeTimeSeconds = lifeTimeSeconds; }

    public LocalDateTime getActivationDate() { return activationDate; }
    public void setActivationDate(LocalDateTime activationDate) { this.activationDate = activationDate; }

    public LocalDateTime getEndingDate() { return endingDate; }
    public void setEndingDate(LocalDateTime endingDate) { this.endingDate = endingDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public String getDigitalSignature() { return digitalSignature; }
    public void setDigitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }
}
