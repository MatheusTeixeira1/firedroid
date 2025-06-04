package br.com.firedroid.service;

import br.com.firedroid.entity.User;
import br.com.firedroid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        // Encrypts the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> updateUser(Long id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setUsername(newUser.getUsername());
                    user.setRole(newUser.getRole());
                    // Doesn't update password directly here (use the specific method)
                    return userRepository.save(user);
                });
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<User> updateUsername(Long id, String newUsername, String password) {
        return userRepository.findById(id)
                .map(user -> {
                    if (password == null || password.trim().isEmpty() || 
                        !passwordEncoder.matches(password, user.getPassword())) {
                        throw new SecurityException("Invalid password");
                    }

                    if (newUsername == null || newUsername.trim().isEmpty()) {
                        throw new IllegalArgumentException("New username cannot be empty");
                    }

                    user.setUsername(newUsername);
                    return userRepository.save(user);
                });
    }

    @Transactional
    public Optional<User> updatePassword(Long id, String newPassword, String currentPassword) {
        return userRepository.findById(id)
                .map(user -> {
                    if (currentPassword == null || currentPassword.trim().isEmpty() || 
                        !passwordEncoder.matches(currentPassword, user.getPassword())) {
                        throw new SecurityException("Invalid current password");
                    }

                    if (newPassword == null || newPassword.trim().isEmpty()) {
                        throw new IllegalArgumentException("New password cannot be empty");
                    }

                    user.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(user);
                });
    }
}