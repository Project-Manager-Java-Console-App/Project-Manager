package org.example.Repository;

import org.example.Exceptions.TaskIdNotFound;
import org.example.Exceptions.UserIdNotFound;
import org.example.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class MySqlTaskUserRepository implements TaskUserRepository {
    private final Connection conn;

    public MySqlTaskUserRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Integer> getUsersInTask(Integer taskId) {
        String query = "SELECT user_id FROM user_task_assigment WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> users = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("user_id");
                    users.add(id);
                }
                return users;
            }
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
    public List<Integer> getAllTasksAssignedToUser(Integer userId)  {
        String query = "SELECT id FROM user_task_assigment WHERE user_id = ?";
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
}
