package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.AlarmAction;
import org.snoozy.snoozyalarm.domain.AlarmActionStatus;
import org.snoozy.snoozyalarm.domain.AlarmActionType;

import java.time.LocalDateTime;

public class AlarmActionResponse {
    private Long id;
    private Long alarmId;
    private Long actorUserId;
    private Long targetUserId;
    private AlarmActionType actionType;
    private AlarmActionStatus status;
    private LocalDateTime executedAt;
    private String messageText;

    public static AlarmActionResponse from(AlarmAction a) {
        AlarmActionResponse r = new AlarmActionResponse();
        r.id = a.getId();
        r.alarmId = a.getAlarmId();
        r.actorUserId = a.getActorUserId();
        r.targetUserId = a.getTargetUserId();
        r.actionType = a.getActionType();
        r.status = a.getStatus();
        r.executedAt = a.getExecutedAt();
        r.messageText = a.getMessageText();
        return r;
    }

    public Long getId() { return id; }
    public Long getAlarmId() { return alarmId; }
    public Long getActorUserId() { return actorUserId; }
    public Long getTargetUserId() { return targetUserId; }
    public AlarmActionType getActionType() { return actionType; }
    public AlarmActionStatus getStatus() { return status; }
    public LocalDateTime getExecutedAt() { return executedAt; }
    public String getMessageText() { return messageText; }
}