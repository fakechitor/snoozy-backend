package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.Alarm;
import org.snoozy.snoozyalarm.domain.AlarmDayOfWeek;

import java.time.LocalDateTime;
import java.util.Set;

public class AlarmResponse {

    private Long id;
    private Long ownerId;
    private String title;
    private LocalDateTime alarmTime;
    private boolean enabled;
    private Set<AlarmDayOfWeek> repeatDays;
    private String soundName;
    private Integer difficultyLevel;
    private boolean isOverslept;
    private LocalDateTime sleepTime;

    public static AlarmResponse from(Alarm alarm) {
        AlarmResponse response = new AlarmResponse();
        response.id = alarm.getId();
        response.ownerId = alarm.getOwnerId();
        response.title = alarm.getTitle();
        response.alarmTime = alarm.getAlarmTime();
        response.enabled = alarm.isEnabled();
        response.repeatDays = alarm.getRepeatDays();
        response.soundName = alarm.getSoundName();
        response.difficultyLevel = alarm.getDifficultyLevel();
        response.isOverslept = alarm.isOverslept();
        response.sleepTime = alarm.getSleepTime();
        return response;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<AlarmDayOfWeek> getRepeatDays() {
        return repeatDays;
    }

    public String getSoundName() {
        return soundName;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public boolean isOverslept() {
        return isOverslept;
    }
    
    public LocalDateTime getSleepTime() {
        return sleepTime;
    }
}