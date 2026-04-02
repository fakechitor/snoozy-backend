package org.snoozy.snoozyuser.repository;

import org.snoozy.snoozyuser.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
