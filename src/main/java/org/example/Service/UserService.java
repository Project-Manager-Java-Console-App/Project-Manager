package org.example.Service;

import org.example.Auth.PasswordUtils;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Repository.UserRepository;
import org.example.model.Users;
import java.sql.SQLException;

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

    public Users loginUser(String username, char[] password) throws SQLException {
        Users user = userRepository.authenticate(username, password);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }

        byte[] salt = user.getSalt();
        byte[] expectedHash = user.getPasswordHash();

        byte[] inputHash = PasswordUtils.hash(password, salt);

        if (!PasswordUtils.slowEquals(expectedHash, inputHash)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user;
    }

    public boolean updateUser(String username,int user_id) throws SQLException{
        Users user = userRepository.findById(user_id);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        return userRepository.update(username,user_id);
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
