package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.Alarm;
import org.snoozy.snoozyalarm.domain.AlarmRepeatPattern;

import java.time.LocalDateTime;

public class AlarmResponse {
    private Long id;
    private Long ownerId;
    private String title;
    private LocalDateTime alarmTime;
    private Boolean enabled;
    private AlarmRepeatPattern repeatPattern;
    private String soundName;
    private Integer difficultyLevel;

    public static AlarmResponse from(Alarm alarm) {
        AlarmResponse r = new AlarmResponse();
        r.id = alarm.getId();
        r.ownerId = alarm.getOwnerId();
        r.title = alarm.getTitle();
        r.alarmTime = alarm.getAlarmTime();
        r.enabled = alarm.getEnabled();
        r.repeatPattern = alarm.getRepeatPattern();
        r.soundName = alarm.getSoundName();
        r.difficultyLevel = alarm.getDifficultyLevel();
        return r;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public String getTitle() { return title; }
    public LocalDateTime getAlarmTime() { return alarmTime; }
    public Boolean getEnabled() { return enabled; }
    public AlarmRepeatPattern getRepeatPattern() { return repeatPattern; }
    public String getSoundName() { return soundName; }
    public Integer getDifficultyLevel() { return difficultyLevel; }
}