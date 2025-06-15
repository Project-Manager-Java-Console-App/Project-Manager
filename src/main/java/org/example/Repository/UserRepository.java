package org.example.Repository;


import org.example.model.Users;

import java.sql.SQLException;

public interface UserRepository {

    Users save(Users user) throws SQLException;
    boolean delete(Users user) throws SQLException;
    boolean update(String username,int user_id) throws SQLException;
    Users findByName(String name) throws SQLException;
    Users findById(Integer id) throws SQLException;
    Users authenticate(String username, char[] password) throws SQLException;
}
