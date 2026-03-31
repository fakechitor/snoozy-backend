package org.snoozy.snoozyuser.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.model.User;
import org.snoozy.snoozyuser.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
