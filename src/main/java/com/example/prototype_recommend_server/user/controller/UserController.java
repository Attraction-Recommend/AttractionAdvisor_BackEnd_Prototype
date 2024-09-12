package com.example.prototype_recommend_server.user.controller;


import com.example.prototype_recommend_server.user.entity.User;
import com.example.prototype_recommend_server.user.entity.UserPreference;
import com.example.prototype_recommend_server.user.dto.UserDTO;
import com.example.prototype_recommend_server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO.toUser());
        return ResponseEntity.ok(UserDTO.fromUser(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User userToUpdate = userDTO.toUser();
        userToUpdate.setId(id);
        User updatedUser = userService.updateUser(userToUpdate);
        return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/preferences")
    public ResponseEntity<UserDTO> addUserPreference(@PathVariable Long id, @RequestBody UserDTO.UserPreferenceDTO preferenceDTO) {
        UserPreference preference = preferenceDTO.toUserPreference();
        User updatedUser = userService.addUserPreference(id, preference);
        return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
    }
}