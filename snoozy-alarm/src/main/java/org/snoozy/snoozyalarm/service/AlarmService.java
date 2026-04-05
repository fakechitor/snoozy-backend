package org.snoozy.snoozyalarm.service;

import org.snoozy.snoozyalarm.api.dto.CreateAlarmRequest;
import org.snoozy.snoozyalarm.api.dto.GrantPermissionRequest;
import org.snoozy.snoozyalarm.api.dto.TriggerAlarmRequest;
import org.snoozy.snoozyalarm.api.dto.UpdateAlarmRequest;
import org.snoozy.snoozyalarm.domain.*;
import org.snoozy.snoozyalarm.repository.AlarmActionRepository;
import org.snoozy.snoozyalarm.repository.AlarmPermissionRepository;
import org.snoozy.snoozyalarm.repository.AlarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    public List<Alarm> getOwnAlarms(Long ownerId) {
        return alarmRepository.findAllByOwnerIdOrderByAlarmTimeAsc(ownerId);
    }

    public Alarm createOwnAlarm(Long ownerId, CreateAlarmRequest request) {
        if (request == null || request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
        if (request.getAlarmTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alarmTime is required");
        }

        Alarm alarm = new Alarm();
        alarm.setOwnerId(ownerId);
        alarm.setTitle(request.getTitle());
        alarm.setAlarmTime(request.getAlarmTime());
        alarm.setEnabled(request.getEnabled());
        alarm.setRepeatPattern(request.getRepeatPattern());
        alarm.setSoundName(request.getSoundName());
        alarm.setDifficultyLevel(request.getDifficultyLevel());

        return alarmRepository.save(alarm);
    }

    public Alarm updateOwnAlarm(Long ownerId, Long alarmId, UpdateAlarmRequest request) {
        Alarm alarm = getOwnedAlarm(ownerId, alarmId);

        if (request.getTitle() != null) alarm.setTitle(request.getTitle());
        if (request.getAlarmTime() != null) alarm.setAlarmTime(request.getAlarmTime());
        if (request.getEnabled() != null) alarm.setEnabled(request.getEnabled());
        if (request.getRepeatPattern() != null) alarm.setRepeatPattern(request.getRepeatPattern());
        if (request.getSoundName() != null) alarm.setSoundName(request.getSoundName());
        if (request.getDifficultyLevel() != null) alarm.setDifficultyLevel(request.getDifficultyLevel());

        return alarmRepository.save(alarm);
    }

    public void deleteOwnAlarm(Long ownerId, Long alarmId) {
        Alarm alarm = getOwnedAlarm(ownerId, alarmId);
        alarmRepository.delete(alarm);
    }

    public AlarmPermission grantPermission(Long ownerId, GrantPermissionRequest request) {
        if (request.getTargetUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "targetUserId is required");
        }
        if (request.getPermissionType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "permissionType is required");
        }

        AlarmPermission permission = new AlarmPermission();
        permission.setOwnerId(ownerId);
        permission.setTargetUserId(request.getTargetUserId());
        permission.setPermissionType(request.getPermissionType());
        permission.setActive(true);

        return permissionRepository.save(permission);
    }

    @Transactional(readOnly = true)
    public List<AlarmPermission> getPermissions(Long ownerId) {
        return permissionRepository.findAllByOwnerId(ownerId);
    }

    public AlarmAction triggerFriendAlarm(Long actorUserId, Long alarmId, TriggerAlarmRequest request) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        requirePermission(alarm.getOwnerId(), actorUserId, AlarmPermissionType.TRIGGER);

        alarm.setEnabled(true);
        alarmRepository.save(alarm);

        AlarmAction action = new AlarmAction();
        action.setAlarmId(alarm.getId());
        action.setActorUserId(actorUserId);
        action.setTargetUserId(alarm.getOwnerId());
        action.setActionType(AlarmActionType.TRIGGER_NOW);
        action.setStatus(AlarmActionStatus.EXECUTED);
        action.setExecutedAt(LocalDateTime.now());
        action.setMessageText(request != null ? request.getMessageText() : null);

        return actionRepository.save(action);
    }

    public AlarmAction setFriendAlarmEnabled(Long actorUserId, Long alarmId, boolean enabled) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        requirePermission(alarm.getOwnerId(), actorUserId, AlarmPermissionType.ENABLE_DISABLE);

        alarm.setEnabled(enabled);
        alarmRepository.save(alarm);

        AlarmAction action = new AlarmAction();
        action.setAlarmId(alarm.getId());
        action.setActorUserId(actorUserId);
        action.setTargetUserId(alarm.getOwnerId());
        action.setActionType(enabled ? AlarmActionType.ENABLE : AlarmActionType.DISABLE);
        action.setStatus(AlarmActionStatus.EXECUTED);
        action.setExecutedAt(LocalDateTime.now());

        return actionRepository.save(action);
    }

    @Transactional(readOnly = true)
    public List<AlarmAction> getIncomingActions(Long ownerId) {
        return actionRepository.findAllByTargetUserIdOrderByCreatedAtDesc(ownerId);
    }

    private Alarm getOwnedAlarm(Long ownerId, Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));

        if (!alarm.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Alarm does not belong to current user");
        }

        return alarm;
    }

    private void requirePermission(Long ownerId, Long actorUserId, AlarmPermissionType permissionType) {
        permissionRepository.findByOwnerIdAndTargetUserIdAndPermissionTypeAndActiveTrue(ownerId, actorUserId, permissionType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No permission for this action"));
    }
}