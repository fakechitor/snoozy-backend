package org.snoozy.snoozyuser.repository;

import org.snoozy.snoozyuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByGoogleSub(String googleSub);
}
