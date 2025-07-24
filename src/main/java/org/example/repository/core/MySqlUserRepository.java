package org.example.repository.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataBase.DatabaseManager;
import org.example.model.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

public class MySqlUserRepository implements UserRepository {
    private final Connection conn = DatabaseManager.getInstance().getConnection();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Logger logger = LogManager.getLogger(MySqlUserRepository.class);


    @Override
    public Users save(Users user) {
        String query = "Insert into users(username,password) values(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPasswordHash());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Failed attempt for registration");
                return null;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            return user;
        } catch (SQLException e) {
            logger.error("Failed to save user");
        }
        return null;
    }

    @Override
    public boolean delete(Users user) {
        String query = "Delete from users where id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete user {}", e.getMessage());
        }
        return false;
    }

    @Override
    public Users update(Users users, int id) {

        String sql = "Update users set username=? where id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, users.getName());
            statement.setInt(2, id);
            int rows = statement.executeUpdate();
            if (rows == 0) {
                logger.error("Failed to update user");
            }
        } catch (SQLException e) {
            logger.error("Failed to update user {}", e.getMessage());
        }
        return users;
    }


    @Override
    public Users findByName(String name) {
        String query = "Select * from users where username=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                Users u = Users.createUser(name);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            logger.error("Failed to find user by name {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Users findById(Integer id) {
        String query = "Select username from users where id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String username = rs.getString("username");
                Users u = Users.createUser(username);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            logger.error("Failed to find user by id {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Users authenticate(String username, String password) {
        String sql = "SELECT id,password from users where username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                int id = rs.getInt("id");
                String hash = rs.getString("password");


                if (passwordEncoder.matches(password, hash)) {
                    Users u = Users.loginUser(username, hash);
                    u.setId(id);
                    return u;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to authenticate");
        }
        return null;
    }
}
