package org.snoozy.snoozyalarm.api.dto;

import org.snoozy.snoozyalarm.domain.AlarmRepeatPattern;
import java.time.LocalDateTime;

public class UpdateAlarmRequest {
    private String title;
    private LocalDateTime alarmTime;
    private Boolean enabled;
    private AlarmRepeatPattern repeatPattern;
    private String soundName;
    private Integer difficultyLevel;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getAlarmTime() { return alarmTime; }
    public void setAlarmTime(LocalDateTime alarmTime) { this.alarmTime = alarmTime; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public AlarmRepeatPattern getRepeatPattern() { return repeatPattern; }
    public void setRepeatPattern(AlarmRepeatPattern repeatPattern) { this.repeatPattern = repeatPattern; }

    public String getSoundName() { return soundName; }
    public void setSoundName(String soundName) { this.soundName = soundName; }

    public Integer getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(Integer difficultyLevel) { this.difficultyLevel = difficultyLevel; }
}