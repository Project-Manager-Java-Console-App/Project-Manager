package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UserIdNotFound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MySqlProjectUserRepository implements ProjectUserRepository {
    private final Connection conn;

    public MySqlProjectUserRepository(Connection connection) {
        this.conn = connection;
    }
    @Override
    public List<Integer> getUsersInProject(Integer projectId) throws ProjectIdNotFound {
        String query = "SELECT user_id FROM user_project_assignment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> users = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("user_id");
                    users.add(id);
                }
                return users;
            }
        }catch (SQLException e){
            throw new ProjectIdNotFound();
        }

    }

    @Override
    public boolean removeUserFromProject(Integer projectId, Integer userId) throws SQLException {
        String query = "DELETE FROM user_project_assignment WHERE id = ? and user_id =?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            System.err.println("SQL error while deleting user to project: " + e.getMessage());
            throw new SQLException("Cannot assign user to project. Check if IDs are valid.", e);
        }
    }

    @Override
    public boolean addUserToProject(Integer projectId, Integer userId) throws SQLException {
        String query = "INSERT INTO user_project_assignment (id, user_id, assigment_date) VALUES (?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            stmt.setObject(3, LocalDate.now());

            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            System.err.println("SQL error while assigning user to project: " + e.getMessage());
            throw new SQLException("Cannot assign user to project. Check if IDs are valid.", e);
        }
    }

    @Override
    public List<Integer> getAllProjectsCreatedByUser(Integer userId) throws UserIdNotFound {
        String query = "SELECT id FROM project WHERE created_by = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> ids= new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    ids.add(id);
                }
                return ids;
            }
        }catch (SQLException e){
            throw new UserIdNotFound();
        }
    }

    @Override
    public List<Integer> getAllProjectsWhereUserIsAdded(Integer userId) throws UserIdNotFound {
        String query = "SELECT id FROM user_project_assignment WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> users = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("user_id");
                    users.add(id);
                }
                return users;
            }
        }catch (SQLException e){
            throw new ProjectIdNotFound();
        }
    }
}
