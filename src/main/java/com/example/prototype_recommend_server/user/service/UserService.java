package com.example.prototype_recommend_server.user;

import com.example.prototype_recommend_server.user.entity.User;
import com.example.prototype_recommend_server.user.entity.UserPreference;
import com.example.prototype_recommend_server.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        User existingUser = getUserById(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        // Note: Password update should be handled separately for security reasons
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User addUserPreference(Long userId, UserPreference preference) {
        User user = getUserById(userId);
        preference.setUser(user);
        user.getPreferences().add(preference);
        return userRepository.save(user);
    }
}