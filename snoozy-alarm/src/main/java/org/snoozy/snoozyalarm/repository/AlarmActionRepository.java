package org.snoozy.snoozyalarm.repository;

import org.snoozy.snoozyalarm.domain.AlarmAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmActionRepository extends JpaRepository<AlarmAction, Long> {
    List<AlarmAction> findAllByTargetUserIdOrderByCreatedAtDesc(Long targetUserId);
}