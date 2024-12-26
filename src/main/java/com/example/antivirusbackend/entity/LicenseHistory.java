package com.example.antivirusbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "license_history")
public class LicenseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "license_id", nullable = false)
    private License license;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String status;
    private LocalDateTime changeDate;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public License getLicense() { return license; }
    public void setLicense(License license) { this.license = license; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getChangeDate() { return changeDate; }
    public void setChangeDate(LocalDateTime changeDate) { this.changeDate = changeDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
