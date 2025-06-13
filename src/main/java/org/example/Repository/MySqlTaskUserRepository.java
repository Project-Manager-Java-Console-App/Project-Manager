package org.example.Repository;

import org.example.Exceptions.TaskIdNotFound;
import org.example.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.example.Repository.AssigmentHelper.getIntegers;
import static org.example.Repository.AssigmentHelper.getUsers;

public class MySqlTaskUserRepository implements TaskUserRepository {
    private final Connection conn;

    public MySqlTaskUserRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Users> getUsersInTask(Integer taskId) throws SQLException {
        String query = "SELECT * FROM user_task_assigment WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            return getUsers(taskId, stmt);
        }catch (SQLException e) {
            throw new TaskIdNotFound();
        }
    }

    @Override
    public boolean removeUserFromTask(Integer taskId, Integer userId) throws SQLException {
        String query = "DELETE FROM user_task_assigment WHERE id = ? and user_id =?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            System.err.println("SQL error while deleting user to task: " + e.getMessage());
            throw new SQLException("Cannot assign user to Task. Check if IDs are valid.", e);
        }
    }

    @Override
    public boolean addUserToTask(Integer taskId, Integer userId) throws SQLException {
        String query = "Insert into user_task_assigment (id, user_id,assigment_date) VALUES (?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);
            stmt.setObject(3, LocalDate.now());
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            System.err.println("SQL error while assigning user to task: " + e.getMessage());
            throw new SQLException("Cannot assign user to Task. Check if IDs are valid.", e);
        }
    }

    @Override
    public List<Integer> getAllTasksAssignedToUser(Integer userId) throws SQLException {
        String query = "SELECT * FROM user_task_assigment WHERE user_id = ?";
        return getIntegers(userId, query, conn);
    }
}
