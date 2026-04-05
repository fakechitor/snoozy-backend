package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.AlarmDayOfWeek;

import java.time.LocalDateTime;
import java.util.Set;

public class UpdateAlarmRequest {

    private String title;
    private LocalDateTime alarmTime;
    private Boolean enabled;
    private Set<AlarmDayOfWeek> repeatDays;
    private String soundName;
    private Integer difficultyLevel;
    private Boolean isOverslept;

    public String getTitle() {
        return title;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public Boolean getEnabled() {
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

    public Boolean getIsOverslept() {
        return isOverslept;
    }
}