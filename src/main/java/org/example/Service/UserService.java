package org.example.Service;

import org.example.Auth.PasswordUtils;
import org.example.Repository.UserRepository;
import org.example.model.Users;


public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users registerUser(String username, String password) {
        if (userRepository.findByName(username) != null) {
            System.err.println("Username already exists");
            return null;
        }
        return userRepository.save(Users.registerNew(username, password));

    }

    public Users loginUser(String username, char[] password) {
        Users user = userRepository.authenticate(username, password);
        if (user == null) {
            System.err.println("Invalid username or password");
            return null;
        }

        byte[] salt = user.getSalt();
        byte[] expectedHash = user.getPasswordHash();

        byte[] inputHash = PasswordUtils.hash(password, salt);

        if (!PasswordUtils.slowEquals(expectedHash, inputHash)) {
            System.err.println("Invalid password");
            return null;
        }
        return user;
    }

    public Users updateUser(Users username, int user_id) {
        Users user = userRepository.findById(user_id);
        if (user == null) {
            System.err.println("Failed to update user");
            return null;
        }
        return userRepository.update(username, user_id);
    }

    public boolean deleteUser(Integer id) {
        Users user = userRepository.findById(id);
        if (user == null) {
            System.err.println("Failed to delete user");
            return false;
        }
        return userRepository.delete(user);
    }

    public Users findByName(String username) {
        Users user = userRepository.findByName(username);
        if (user == null) {
            System.err.println("Failed to find user by name");
            return null;
        }
        return user;
    }

    public Users findById(Integer id) {
        Users user = userRepository.findById(id);
        if (user == null) {
            System.err.println("Failed to find user by id");
            return null;
        }
        return user;
    }
}
