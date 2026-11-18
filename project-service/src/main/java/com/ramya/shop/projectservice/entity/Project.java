package com.ramya.shop.projectservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String createdBy;
    private String createdByUname;

    @Column(name = "normalized_name", nullable = false, length = 100)
    private String normalizedName;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.normalizedName = this.name.trim().toLowerCase(Locale.ROOT);
    }

    @PreUpdate
    public void onUpdate() {
        this.normalizedName = this.name.trim().toLowerCase(Locale.ROOT);
    }


}
