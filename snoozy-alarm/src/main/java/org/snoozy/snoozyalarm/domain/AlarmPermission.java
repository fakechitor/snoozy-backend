package org.snoozy.snoozyalarm.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alarm_permissions")
public class AlarmPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "target_user_id")
    private Long targetUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", length = 32)
    private AlarmPermissionType permissionType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
    }

    public Long getId() { return id; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public Long getTargetUserId() { return targetUserId; }

    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }
    public AlarmPermissionType getPermissionType() { return permissionType; }

    public void setPermissionType(AlarmPermissionType permissionType) { this.permissionType = permissionType; }
    public Boolean getIsActive() { return isActive; }

    public void setIsActive(Boolean active) { this.isActive = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}