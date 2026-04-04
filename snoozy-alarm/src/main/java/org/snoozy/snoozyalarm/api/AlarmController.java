package org.snoozy.snoozyalarm.api;

import org.snoozy.snoozyalarm.api.dto.*;
import org.snoozy.snoozyalarm.domain.AlarmPermission;
import org.snoozy.snoozyalarm.service.AlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;
    private final CurrentUserProvider currentUserProvider;

    public AlarmController(AlarmService alarmService, CurrentUserProvider currentUserProvider) {
        this.alarmService = alarmService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public List<AlarmResponse> getOwnAlarms() {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return alarmService.getOwnAlarms(currentUserId).stream().map(AlarmResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlarmResponse createOwnAlarm(@RequestBody CreateAlarmRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return AlarmResponse.from(alarmService.createOwnAlarm(currentUserId, request));
    }

    @PatchMapping("/{alarmId}")
    public AlarmResponse updateOwnAlarm(@PathVariable Long alarmId, @RequestBody UpdateAlarmRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return AlarmResponse.from(alarmService.updateOwnAlarm(currentUserId, alarmId, request));
    }

    @DeleteMapping("/{alarmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnAlarm(@PathVariable Long alarmId) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        alarmService.deleteOwnAlarm(currentUserId, alarmId);
    }

    @PostMapping("/permissions")
    @ResponseStatus(HttpStatus.CREATED)
    public AlarmPermission grantPermission(@RequestBody GrantPermissionRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return alarmService.grantPermission(currentUserId, request);
    }

    @GetMapping("/permissions")
    public List<AlarmPermission> getPermissions() {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return alarmService.getPermissions(currentUserId);
    }

    @PostMapping("/{alarmId}/trigger")
    @ResponseStatus(HttpStatus.CREATED)
    public AlarmActionResponse triggerFriendAlarm(
            @PathVariable Long alarmId,
            @RequestBody(required = false) TriggerAlarmRequest request
    ) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return AlarmActionResponse.from(alarmService.triggerFriendAlarm(currentUserId, alarmId, request));
    }

    @PostMapping("/{alarmId}/enable")
    public AlarmActionResponse enableFriendAlarm(@PathVariable Long alarmId) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return AlarmActionResponse.from(alarmService.setFriendAlarmEnabled(currentUserId, alarmId, true));
    }

    @PostMapping("/{alarmId}/disable")
    public AlarmActionResponse disableFriendAlarm(@PathVariable Long alarmId) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return AlarmActionResponse.from(alarmService.setFriendAlarmEnabled(currentUserId, alarmId, false));
    }

    @GetMapping("/actions/incoming")
    public List<AlarmActionResponse> getIncomingActions() {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        return alarmService.getIncomingActions(currentUserId).stream().map(AlarmActionResponse::from).toList();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "module", "snoozy-alarm");
    }
}