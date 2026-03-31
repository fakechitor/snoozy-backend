package org.snoozy.snoozyuser.repository;

import org.snoozy.snoozyuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
