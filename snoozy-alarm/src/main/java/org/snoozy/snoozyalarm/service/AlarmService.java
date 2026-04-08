package org.snoozy.snoozyalarm.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.snoozy.snoozyalarm.api.dto.CreateAlarmRequest;
import org.snoozy.snoozyalarm.api.dto.GrantPermissionRequest;
import org.snoozy.snoozyalarm.api.dto.TriggerAlarmRequest;
import org.snoozy.snoozyalarm.api.dto.UpdateAlarmRequest;
import org.snoozy.snoozyalarm.domain.Alarm;
import org.snoozy.snoozyalarm.domain.AlarmAction;
import org.snoozy.snoozyalarm.domain.AlarmActionStatus;
import org.snoozy.snoozyalarm.domain.AlarmActionType;
import org.snoozy.snoozyalarm.domain.AlarmPermission;
import org.snoozy.snoozyalarm.domain.AlarmPermissionType;
import org.snoozy.snoozyalarm.repository.AlarmActionRepository;
import org.snoozy.snoozyalarm.repository.AlarmPermissionRepository;
import org.snoozy.snoozyalarm.repository.AlarmRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmPermissionRepository permissionRepository;
    private final AlarmActionRepository actionRepository;

    public AlarmService(
            AlarmRepository alarmRepository,
            AlarmPermissionRepository permissionRepository,
            AlarmActionRepository actionRepository
    ) {
        this.alarmRepository = alarmRepository;
        this.permissionRepository = permissionRepository;
        this.actionRepository = actionRepository;
    }

    public List<Alarm> getOwnAlarms(Long ownerId) {
        return alarmRepository.findAllByOwnerIdOrderByAlarmTimeAsc(ownerId);
    }

    public List<Alarm> getAlarmsByUserId(Long userId) {
        return alarmRepository.findAllByOwnerIdOrderByAlarmTimeAsc(userId);
    }

    public Alarm createOwnAlarm(Long ownerId, CreateAlarmRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        if (request.getAlarmTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alarm time is required");
        }

        LocalDateTime now = LocalDateTime.now();

        Alarm alarm = new Alarm();
        alarm.setOwnerId(ownerId);
        alarm.setTitle(request.getTitle());
        alarm.setAlarmTime(request.getAlarmTime());
        alarm.setEnabled(request.getEnabled() == null || request.getEnabled());
        alarm.setRepeatDays(request.getRepeatDays());
        alarm.setSoundName(request.getSoundName());
        alarm.setDifficultyLevel(request.getDifficultyLevel());
        alarm.setOverslept(false);
        alarm.setCreatedAt(now);
        alarm.setUpdatedAt(now);

        return alarmRepository.save(alarm);
    }

    public Alarm updateOwnAlarm(Long ownerId, Long alarmId, UpdateAlarmRequest request) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        if (!alarm.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your alarm");
        }

        if (request.getTitle() != null) {
            alarm.setTitle(request.getTitle());
        }
        if (request.getAlarmTime() != null) {
            alarm.setAlarmTime(request.getAlarmTime());
        }
        if (request.getEnabled() != null) {
            alarm.setEnabled(request.getEnabled());
        }
        if (request.getRepeatDays() != null) {
            alarm.setRepeatDays(request.getRepeatDays());
        }
        if (request.getSoundName() != null) {
            alarm.setSoundName(request.getSoundName());
        }
        if (request.getDifficultyLevel() != null) {
            alarm.setDifficultyLevel(request.getDifficultyLevel());
        }
        if (request.getIsOverslept() != null) {
            alarm.setOverslept(request.getIsOverslept());
        }

        alarm.setUpdatedAt(LocalDateTime.now());
        return alarmRepository.save(alarm);
    }

    public void deleteOwnAlarm(Long ownerId, Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        if (!alarm.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your alarm");
        }

        alarmRepository.delete(alarm);
    }

    public AlarmPermission grantPermission(Long ownerId, GrantPermissionRequest request) {
        if (request.getTargetUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target user id is required");
        }
        if (request.getPermissionType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission type is required");
        }

        AlarmPermission permission = new AlarmPermission();
        permission.setOwnerId(ownerId);
        permission.setTargetUserId(request.getTargetUserId());
        permission.setPermissionType(request.getPermissionType());
        permission.setIsActive(true);
        permission.setCreatedAt(LocalDateTime.now());

        return permissionRepository.save(permission);
    }

    public List<AlarmPermission> getPermissions(Long ownerId) {
        return permissionRepository.findAllByOwnerIdAndIsActiveTrue(ownerId);
    }

    public AlarmAction triggerFriendAlarm(Long actorUserId, Long alarmId, TriggerAlarmRequest request) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        validatePermission(alarm.getOwnerId(), actorUserId, AlarmPermissionType.TRIGGER);

        AlarmAction action = new AlarmAction();
        action.setAlarmId(alarm.getId());
        action.setActorUserId(actorUserId);
        action.setTargetUserId(alarm.getOwnerId());
        action.setActionType(AlarmActionType.TRIGGER_NOW);
        action.setStatus(AlarmActionStatus.EXECUTED);
        action.setExecutedAt(LocalDateTime.now());
        action.setMessageText(request != null ? request.getMessageText() : null);
        action.setCreatedAt(LocalDateTime.now());

        return actionRepository.save(action);
    }

    public AlarmAction setFriendAlarmEnabled(Long actorUserId, Long alarmId, boolean enabled) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        validatePermission(alarm.getOwnerId(), actorUserId, AlarmPermissionType.ENABLE_DISABLE);

        alarm.setEnabled(enabled);
        alarm.setUpdatedAt(LocalDateTime.now());
        alarmRepository.save(alarm);

        AlarmAction action = new AlarmAction();
        action.setAlarmId(alarm.getId());
        action.setActorUserId(actorUserId);
        action.setTargetUserId(alarm.getOwnerId());
        action.setActionType(enabled ? AlarmActionType.ENABLE : AlarmActionType.DISABLE);
        action.setStatus(AlarmActionStatus.EXECUTED);
        action.setExecutedAt(LocalDateTime.now());
        action.setCreatedAt(LocalDateTime.now());

        return actionRepository.save(action);
    }

    public List<AlarmAction> getIncomingActions(Long ownerId) {
        return actionRepository.findAllByTargetUserIdOrderByCreatedAtDesc(ownerId);
    }

    private void validatePermission(Long ownerId, Long actorUserId, AlarmPermissionType permissionType) {
        boolean allowed = permissionRepository.existsByOwnerIdAndTargetUserIdAndPermissionTypeAndIsActiveTrue(
                ownerId,
                actorUserId,
                permissionType
        );

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied");
        }
    }
}