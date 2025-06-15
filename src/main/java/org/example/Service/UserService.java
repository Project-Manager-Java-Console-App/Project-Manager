package org.example.Service;

import org.example.Auth.PasswordUtils;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Repository.UserRepository;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Base64;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users registerUser(String username, String password) throws UsernameAlreadyExistsException, SQLException {
        if(userRepository.findByName(username)!=null){
            throw new UsernameAlreadyExistsException(username);
        }
        return userRepository.save(Users.registerNew(username,password));

    }

    public Users loginUser(String username, String password) throws SQLException {
        Users user = userRepository.findByName(username);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }

        byte[] salt = Base64.getDecoder().decode(user.getSalt());
        byte[] expectedHash = Base64.getDecoder().decode(user.getPasswordHash());

        byte[] inputHash = PasswordUtils.hash(password.toCharArray(), salt);

        if (!PasswordUtils.slowEquals(expectedHash, inputHash)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user;
    }

    public boolean updateUser(String username,int user_id) throws SQLException{
        Users user = userRepository.findByName(username);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        return userRepository.update(user,user_id);
    }

    public boolean deleteUser(Integer id) throws SQLException{
        Users user = userRepository.findById(id);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        return userRepository.delete(user);
    }

    public Users findByName(String username) throws SQLException {
        Users user = userRepository.findByName(username);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public Users findById(Integer id) throws SQLException, UsernameAlreadyExistsException {
        Users user = userRepository.findById(id);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }
}
