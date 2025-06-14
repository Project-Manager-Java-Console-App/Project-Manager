package org.example.Repository;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Users;

import java.sql.*;

public class MySqlUserRepository implements UserRepository {
    private final Connection conn;

    public MySqlUserRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Users save(Users user) throws SQLException {
        String query = "Insert into users(username,password,salt) values(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getSalt());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed attempt for registration");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            return user;
        }catch (SQLException e) {
            throw new SQLException("Error while saving user: ", e);
        }

    }

    @Override
    public boolean delete(Users user) throws SQLException {
        String query = "Delete from users where id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new SQLException("Error while deleting user: ", e);
        }
    }

    @Override
    public boolean update(Users user) throws SQLException {
        String sql = "Update users set username=? where id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getId());
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new SQLException("Error while updating user: ", e);
        }
    }

    @Override
    public Users findByName(String name)throws SQLException{
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
        }catch (SQLException e){
            throw new SQLException(name);
        }
    }

    @Override
    public Users findById(Integer id) throws SQLException {
        String query = "Select * from users where id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String username = rs.getString("username");
                Users u = Users.createUser(username);
                u.setId(id);
                return u;
            }
        }catch (SQLException e){
            throw new SQLException("Error while finding user by ID: ", e);
        }

    }
}
