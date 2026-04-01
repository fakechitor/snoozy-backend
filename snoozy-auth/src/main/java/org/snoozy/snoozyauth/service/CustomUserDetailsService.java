package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.model.CustomUserDetails;
import org.snoozy.snoozyauth.model.User;
import org.snoozy.snoozyauth.repository.UserRepository;
import org.snoozy.snoozyauth.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername((username)).orElseThrow(
                () -> new UserNotFoundException("Wrong credentials"));

        return new CustomUserDetails(user);
    }
}
