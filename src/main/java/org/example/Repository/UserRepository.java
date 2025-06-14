package org.example.Repository;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Users;

import java.sql.SQLException;

public interface UserRepository {

    Users save(Users user) throws SQLException;
    boolean delete(Users user) throws SQLException;
    boolean update(Users user) throws SQLException;
    Users findByName(String name) throws SQLException;
    Users findById(Integer id) throws SQLException;
}
