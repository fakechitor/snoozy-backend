package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.repository.UserRepository;
import org.snoozy.snoozyauth.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
