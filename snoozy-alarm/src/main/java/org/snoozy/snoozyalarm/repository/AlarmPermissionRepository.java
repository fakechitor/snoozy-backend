package org.snoozy.snoozyalarm.repository;

import org.snoozy.snoozyalarm.domain.AlarmPermission;
import org.snoozy.snoozyalarm.domain.AlarmPermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmPermissionRepository extends JpaRepository<AlarmPermission, Long> {
    List<AlarmPermission> findAllByOwnerId(Long ownerId);

    Optional<AlarmPermission> findByOwnerIdAndTargetUserIdAndPermissionTypeAndIsActiveTrue(
            Long ownerId,
            Long targetUserId,
            AlarmPermissionType permissionType
    );

    boolean existsByOwnerIdAndTargetUserIdAndPermissionTypeAndIsActiveTrue(Long ownerId, Long actorUserId, AlarmPermissionType permissionType);

    List<AlarmPermission> findAllByOwnerIdAndIsActiveTrue(Long ownerId);
}