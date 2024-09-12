package com.example.prototype_recommend_server.user.dto;


import com.example.prototype_recommend_server.user.entity.User;
import com.example.prototype_recommend_server.user.entity.UserPreference;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Set<UserPreferenceDTO> preferences;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPreferences(user.getPreferences().stream()
                .map(UserPreferenceDTO::fromUserPreference)
                .collect(Collectors.toSet()));
        return dto;
    }

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        // Note: password should be handled separately for security reasons
        return user;
    }

    @Data
    public static class UserPreferenceDTO {
        private Long id;
        private String category;
        private Integer preferenceLevel;

        public static UserPreferenceDTO fromUserPreference(UserPreference preference) {
            UserPreferenceDTO dto = new UserPreferenceDTO();
            dto.setId(preference.getId());
            dto.setCategory(preference.getCategory());
            dto.setPreferenceLevel(preference.getPreferenceLevel());
            return dto;
        }

        public UserPreference toUserPreference() {
            UserPreference preference = new UserPreference();
            preference.setId(this.id);
            preference.setCategory(this.category);
            preference.setPreferenceLevel(this.preferenceLevel);
            return preference;
        }
    }
}