package org.snoozy.snoozygroup.repository;

import org.snoozy.snoozygroup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
