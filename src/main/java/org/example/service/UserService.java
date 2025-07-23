package org.example.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Users;
import org.example.repository.core.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(UserService.class);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users registerUser(String username, String password) {
        if (username == null || password == null) {
            logger.error("Username or password are null!");
            return null;
        }
        if (userRepository.findByName(username) != null) {
            System.err.println("Username already exists");
            logger.error("Username '{}' already exists", username);
            return null;
        }
        logger.info("Registering new user");
        return userRepository.save(Users.registerNew(username, password));

    }

    public Users loginUser(String username, String password) {
        Users user = userRepository.authenticate(username, password);
        if (user == null) {
            System.err.println("Invalid username or password");
            logger.error("Invalid username or password");
            return null;
        }

        String expectedHash = user.getPasswordHash();
        if (!passwordEncoder.matches(password, expectedHash)) {
            System.err.println("Invalid username or password");
            logger.error("Invalid password");
            return null;
        }
        logger.info("Logging user");
        return user;
    }

    public Users updateUser(Users username, int user_id) {
        Users user = userRepository.findById(user_id);
        if (user == null) {
            System.err.println("Failed to update user");
            logger.error("Failed to update user. The user is not found by id and it is null!");
            return null;
        }
        logger.info("Updating user");
        return userRepository.update(username, user_id);
    }

    public boolean deleteUser(Integer id) {
        Users user = userRepository.findById(id);
        if (user == null) {
            System.err.println("Failed to delete user");
            logger.error("Failed to delete user. The user is not found by id and it is null!");
            return false;
        }
        logger.info("Deleting user");
        return userRepository.delete(user);
    }

    public Users findByName(String username) {
        Users user = userRepository.findByName(username);
        if (user == null) {
            System.err.println("Failed to find user by name");
            logger.error("Failed to find user by name");
            return null;
        }
        logger.info("Finding user by name");
        return user;
    }

    public Users findById(Integer id) {
        Users user = userRepository.findById(id);
        if (user == null) {
            System.err.println("Failed to find user by id");
            logger.error("Failed to find user by id");
            return null;
        }
        logger.info("Finding user by id");
        return user;
    }
}
