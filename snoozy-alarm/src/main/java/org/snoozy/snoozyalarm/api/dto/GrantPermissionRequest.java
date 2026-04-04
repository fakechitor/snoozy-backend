package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.AlarmPermissionType;

public class GrantPermissionRequest {
    private Long targetUserId;
    private AlarmPermissionType permissionType;

    public Long getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }

    public AlarmPermissionType getPermissionType() { return permissionType; }
    public void setPermissionType(AlarmPermissionType permissionType) { this.permissionType = permissionType; }
}