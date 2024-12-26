package com.example.antivirusbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private LicenseType licenseType;

    @Column(name = "first_activation_date")
    private LocalDateTime firstActivationDate;

    @Column(name = "ending_date", nullable = true)
    private LocalDateTime endingDate;

    private boolean blocked;

    @Column(name = "device_count", nullable = false)
    private int deviceCount;

    private int duration;

    private String description;

    // Геттеры и сеттеры

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LicenseType getLicenseType() { return licenseType; }
    public void setLicenseType(LicenseType licenseType) { this.licenseType = licenseType; }

    public LocalDateTime getFirstActivationDate() { return firstActivationDate; }
    public void setFirstActivationDate(LocalDateTime firstActivationDate) { this.firstActivationDate = firstActivationDate; }

    public LocalDateTime getEndingDate() { return endingDate; }
    public void setEndingDate(LocalDateTime endingDate) { this.endingDate = endingDate; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public int getDeviceCount() { return deviceCount; }
    public void setDeviceCount(int deviceCount) { this.deviceCount = deviceCount; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
