package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UserIdNotFound;
import org.example.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.example.Repository.AssigmentHelper.getIntegers;
import static org.example.Repository.AssigmentHelper.getUsers;

public class MySqlProjectUserRepository implements ProjectUserRepository {
    private final Connection conn;

    public MySqlProjectUserRepository(Connection connection) {
        this.conn = connection;
    }
    @Override
    public List<Users> getUsersInProject(Integer projectId) throws ProjectIdNotFound {
        String query = "SELECT * FROM user_project_assignment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            return getUsers(projectId, stmt);
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
    public List<Integer> getAllProjectsAssignedToUser(Integer userId) throws UserIdNotFound {
        String query = "SELECT * FROM user_project_assignment WHERE user_id = ?";
        return getIntegers(userId, query, conn);

    }
}
