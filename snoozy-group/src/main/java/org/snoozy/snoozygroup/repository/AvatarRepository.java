package org.snoozy.snoozygroup.repository;

import org.snoozy.snoozygroup.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {
}
