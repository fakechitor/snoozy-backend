package org.snoozy.snoozyalarm.repository;

import org.snoozy.snoozyalarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByOwnerIdOrderByAlarmTimeAsc(Long ownerId);
}