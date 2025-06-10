package org.example.ProjectDAO;

import org.example.Auth.PasswordUtils;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.dataBase.AbstractCRUDOperations;
import org.example.model.Users;

import java.sql.*;

public class UsersDAO extends AbstractCRUDOperations<Users,Integer> {

    @Override
    protected String tableName() {
        return "users";
    }

    @Override
    protected Users fromResultSet(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        return user;
    }

    @Override
    protected void fillPreparedStatementForInsert(PreparedStatement ps, Users dto)  {}

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement ps, Users dto,Integer id) throws SQLException {
        ps.setString(1, dto.getUsername());
        ps.setInt(2, id);
    }

    @Override
    protected Integer extractGeneratedKey(ResultSet rs) throws SQLException {
       return rs.getInt(1);
    }

    @Override
    protected String insertSQL() {
        return null;
    }

    @Override
    protected String updateSQL() {
        return "Update "+tableName()+" set username = ? where id = ?";
    }

    @Override
    protected String findByNameSQL() {
        return "Select * from users where username = ?";
    }

    public Integer register(String username,char[] password) throws SQLException, UsernameAlreadyExistsException {
        String sql = "Insert into Users(username,password, salt) values(?,?,?)";
        byte[] salt = PasswordUtils.generateSalt();
        byte[] hash = PasswordUtils.hash(password, salt);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.setBytes(2, hash);
            ps.setBytes(3, salt);

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Registration failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                else throw new SQLException("There is no returned Id");
            }
        }catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameAlreadyExistsException(username);
        }
    }
    public Users authenticate(String username, char[] password) throws SQLException {
        String sql = "SELECT id,password,salt from users where username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                byte[] hash  = rs.getBytes("password");
                byte[] salt  = rs.getBytes("salt");
                byte[] attempt = PasswordUtils.hash(password, salt);

                if (PasswordUtils.slowEquals(hash, attempt)) {
                    Users u = new Users();
                    u.setId(rs.getInt("id"));
                    u.setUsername(username);
                    return u;
                } else {
                    return null;
                }
            }
        }
    }
}
