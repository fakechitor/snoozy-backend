package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.exception.UserAlreadyExistsException;
import org.snoozy.snoozyauth.repository.UserRepository;
import org.snoozy.snoozyauth.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) throws UserAlreadyExistsException {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException("User with that credentials already exists");
        }
    }
}
